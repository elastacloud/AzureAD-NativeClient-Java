package com.elastacloud.aad.adal4jpassive;

import com.nimbusds.jose.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.text.ParseException;
/*
*
 * Created by david on 17/12/14.*/


public class main {
    public static void main(String[] args) throws ParseException, NoSuchAlgorithmException, InvalidKeyException, IOException, ParserConfigurationException, SAXException, TransformerException, XPathExpressionException, CertificateException, JOSEException {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6ImtyaU1QZG1Cdng2OHNrVDgtbVBBQjNCc2VlQSJ9.eyJhdWQiOiJodHRwczovL2dhem9vYjFhZC5vbm1pY3Jvc29mdC5jb20vd2ViYXBwIiwiaXNzIjoiaHR0cHM6Ly9zdHMud2luZG93cy5uZXQvNjdiMTg1ZjMtMGE0ZC00ODE1LTlkNmYtZjUzZmViZDg4OTE1LyIsImlhdCI6MTQxODgwODU4NiwibmJmIjoxNDE4ODA4NTg2LCJleHAiOjE0MTg4MTI0ODYsInZlciI6IjEuMCIsInRpZCI6IjY3YjE4NWYzLTBhNGQtNDgxNS05ZDZmLWY1M2ZlYmQ4ODkxNSIsImFtciI6WyJwd2QiXSwib2lkIjoiYzk2ZWVlMjktYzc2ZS00MTgxLWFkMTctZDU4ODc1ZjkyZGZiIiwidXBuIjoidGVzdHVzZXJAZ2F6b29iMWFkLm9ubWljcm9zb2Z0LmNvbSIsInVuaXF1ZV9uYW1lIjoidGVzdHVzZXJAZ2F6b29iMWFkLm9ubWljcm9zb2Z0LmNvbSIsInN1YiI6IlhIQkkyQnptUHIwUnZuamU1Z3dxTVRhYS1IalhBM0FTdW1CaXBNS0JpblEiLCJmYW1pbHlfbmFtZSI6InVzZXIiLCJnaXZlbl9uYW1lIjoidGVzdCIsImFwcGlkIjoiMGFhZDAzODYtOTk2MC00YjEwLWE4NDktZmNlYzFhNTUyMTAyIiwiYXBwaWRhY3IiOiIwIiwic2NwIjoidXNlcl9pbXBlcnNvbmF0aW9uIiwiYWNyIjoiMSJ9.jgYlm-mRy_mgen9Golgyt8U4ftqKfBb8akvGYYj8YrQSvCgBoIOnsU0PoPMHHNtmHaXVT-XNGxLumzQC0RBRfofeQFMAviDzQEz6vF8Blrz8y7g_rbQ-FHMTtqprwsBsxX6nN2V_QU9VltcP05NCAInE1NUVXQDx3LbfztW\n" +
                "qQCmjtIlOTFr-JlH_GeOVEhlSo2yHJl3_2v9no9_3cWvE-kZV3fTnrDS9KrDkee3rA_T_k2mpm9Xa8SCwjxujtqsRw1NBKd7UAgaSQH8PgRNpsWvUovCzx7Q8ttnBqQL_BPG0gsrDCfWTMhEhdh-i1yQcJl3hL5EnfCTIUnGtet9a9Q";



       // RSASSAVerifier verifier = new RSASSAVerifier();





        String xml = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://services.web.post.list.com\"><soapenv:Header><authInfo xsi:type=\"soap:authentication\" xmlns:soap=\"http://list.com/services/SoapRequestProcessor\"><!--You may enter the following 2 items in any order--><username xsi:type=\"xsd:string\">dfasf@google.com</username><password xsi:type=\"xsd:string\">PfasdfRem91</password></authInfo></soapenv:Header></soapenv:Envelope>";

        String endpoint = "https://login.windows.net/67b185f3-0a4d-4815-9d6f-f53febd88915/federationmetadata/2007-06/federationmetadata.xml";
        String issuer = "https://sts.windows.net/67b185f3-0a4d-4815-9d6f-f53febd88915/";
        String aud = "https://gazoob1ad.onmicrosoft.com/webapp";

        BearerValidator validator = new BearerValidator(endpoint, issuer,aud);
        System.out.println(validator.verify(token));


        /*BufferedInputStream in = new BufferedInputStream(new URL(endpoint).openStream());

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String file = "";
        for(String line; (line = reader.readLine()) != null; ) {
            file += line;
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setNamespaceAware(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

        InputSource is = new InputSource(new StringReader(xml.trim().replaceFirst("^([\\W]+)<","<")));
        Document doc = dBuilder.parse(is);


        System.out.println(file);


        DocumentBuilderFactory domFactory = DocumentBuilderFactory
                .newInstance();
        domFactory.setNamespaceAware(true);
        DocumentBuilder builder = domFactory.newDocumentBuilder();
        Document doc = builder.parse(new InputSource(new StringReader(file.trim().replaceFirst("^([\\W]+)<","<"))));
        doc.normalizeDocument();

        NodeList e = doc.getElementsByTagName("RoleDescriptor");

        String cert1 = null;
        String cert2 = null;

        for(int i=0; i < e.getLength(); i++)
        {
            if(e.item(i).getAttributes().getNamedItem("xsi:type").getNodeValue().equals("fed:SecurityTokenServiceType"))
            {
                cert1 = e.item(i).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getNodeValue();
                cert2 = e.item(i).getChildNodes().item(1).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getNodeValue();

                System.out.println(cert1);
                System.out.println(cert2);
            }
        }


        byte [] decoded = Base64.decode(cert1.replaceAll(X509Factory.BEGIN_CERT, "").replaceAll(X509Factory.END_CERT, ""));

        X509Certificate cert =  (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decoded));

        RSASSAVerifier verifier = new RSASSAVerifier((java.security.interfaces.RSAPublicKey) cert.getPublicKey());
        boolean test = jwt.verify(verifier);


        JSONObject jobj = jwt.getPayload().toJSONObject();

        System.out.println("Verify: " + test);
        System.out.println("AUD: "+ "https://gazoob1ad.onmicrosoft.com/webapp".equals(jobj.get("aud")));
        System.out.println("ISS: " + "https://sts.windows.net/67b185f3-0a4d-4815-9d6f-f53febd88915/".equals(jobj.get("iss")));


 Node n1 = e.item(0).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0);
        Node n2 = e.item(1).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0).getChildNodes().item(0);
        System.out.println(n1.getNodeValue());
        System.out.println(n2.getNodeValue());


 ;
        Node n =e.item(0);
        n.getChildNodes().
        DOMSource domSource = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        System.out.println(writer.toString());

 XPath xpath = XPathFactory.newInstance().newXPath();
        xpath.setNamespaceContext(new NamespaceContext() {

            @Override
            public Iterator getPrefixes(String arg0) {
                return null;
            }

            @Override
            public String getPrefix(String arg0) {
                return null;
            }

            @Override
            public String getNamespaceURI(String arg0) {
                if("ds".equals(arg0)) {
                    return "http://www.w3.org/2000/09/xmldsig#";
                }
                return null;
            }
        });
        // XPath Query for showing all nodes value

        try {
            XPathExpression expr = xpath.compile("/EntityDescriptor");
            Object result = expr.evaluate(doc, XPathConstants.NODESET);
            NodeList nodes = (NodeList) result;
          //  System.out.println(nodes.item(0).getNodeName());
            System.out.println("Got " + nodes.getLength() + " nodes");

            // System.out.println(nodes.item(0).getNodeValue());
        } catch (Exception E) {
            System.out.println(E);
        }
*/
    }
}
