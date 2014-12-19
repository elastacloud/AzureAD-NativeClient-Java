/*******************************************************************************
 * Copyright © Microsoft Open Technologies, Inc.
 * 
 * All Rights Reserved
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION
 * ANY IMPLIED WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A
 * PARTICULAR PURPOSE, MERCHANTABILITY OR NON-INFRINGEMENT.
 * 
 * See the Apache License, Version 2.0 for the specific language
 * governing permissions and limitations under the License.
 ******************************************************************************/
package com.elastacloud.aad.adal4jpassive;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PassiveAuthFilter implements Filter {

    private String issuer = "";
    private String audience = "";
    private String fedEndpoint = "";
    private BearerValidator _validator = null;
    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        String token = ((HttpServletRequest)request).getHeader("authorization");

        if(token == null || token.length() == 0)
        {
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        else {
            if (!_validator.verify(token.replace("Bearer", "").trim())) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
            else {
                ((HttpServletRequest) request).getSession().setAttribute(
                        "token", token.replace("Bearer", "").trim());
                ((HttpServletRequest) request).getSession().setAttribute(
                        "verified", true);

                chain.doFilter(request, response);
            }
        }


    }



    public void init(FilterConfig config) throws ServletException {
        issuer = config.getServletContext().getInitParameter("issuer");
        audience = config.getServletContext().getInitParameter("audience");
        fedEndpoint = config.getServletContext().getInitParameter("feddocendpoint");
        _validator = new BearerValidator(fedEndpoint, issuer, audience);

    }

}
