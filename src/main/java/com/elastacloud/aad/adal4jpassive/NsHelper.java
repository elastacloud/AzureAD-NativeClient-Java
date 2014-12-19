package com.elastacloud.aad.adal4jpassive;


import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class NsHelper {
    static public class AzureAdNamespaceContext implements NamespaceContext {
        final private Map<String, String> prefixMap;

        AzureAdNamespaceContext(Map<String, String> prefixMap) {
            if (prefixMap != null) {
                this.prefixMap = Collections.unmodifiableMap(new HashMap<String, String>(prefixMap));
            } else {
                this.prefixMap = Collections.emptyMap();
            }
        }

        public String getPrefix(String namespaceURI) {
            // TODO Auto-generated method stub
            return null;
        }

        public Iterator getPrefixes(String namespaceURI) {
            // TODO Auto-generated method stub
            return null;
        }

        public String getNamespaceURI(String prefix) {
            if (prefix == null) throw new NullPointerException("Invalid Namespace Prefix");
            else if (prefix.equals(XMLConstants.DEFAULT_NS_PREFIX))
                return "urn:oasis:names:tc:SAML:2.0:metadata";
            else if ("xsi".equals(prefix))
                return "http://www.w3.org/2001/XMLSchema-instance";
            else if ("fed".equals(prefix))
                return "http://docs.oasis-open.org/wsfed/federation/200706";
            else
                return "urn:oasis:names:tc:SAML:2.0:metadata";
        }


    }
}