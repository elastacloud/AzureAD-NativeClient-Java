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

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/secure/passive")
public class PassiveController {

    @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
    public String getPassiveAuth(ModelMap model, HttpServletRequest httpRequest) {
        try {
            HttpSession session = httpRequest.getSession();
            String token = (String) session.getAttribute("token");
            boolean verified = (boolean) session.getAttribute("verified");
            if (verified == false) {
                model.addAttribute("error", new Exception("AuthenticationResult not found in session."));
                return "/error";
            } else {

                try {
                    SignedJWT jwt = SignedJWT.parse(token);
                    String upn = (String) jwt.getPayload().toJSONObject().get("upn");
                    model.addAttribute("username", upn);

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            return "/secure/passive";
        }
        catch(Exception e)
        {
            model.addAttribute("error", new Exception("AuthenticationResult not found in session."));
            return "/error";
        }
    }


}
