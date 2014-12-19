package com.elastacloud.aad.adal4jpassive;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.SignedJWT;
import org.bouncycastle.util.encoders.Base64;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathConstants;

import java.io.*;
import java.net.URL;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

/**
 * Created by david on 17/12/14.
 */
public class BearerValidator {

    private String fedEndpoint;
    private String issuer;
    private String audience;
    private Document _federationMetadata;
    private X509Certificate _cert = null;

    public BearerValidator(String fedEndpoint, String issuer, String audience)
    {
        this.fedEndpoint = fedEndpoint;
        this.issuer = issuer;
        this.audience = audience;
        this._federationMetadata = getFedMetaDataDocument();

        if(_federationMetadata != null)
            _cert = getCert(_federationMetadata);
    }

    public boolean verify(String token)
    {
        if(token == null || token.length() == 0)
            return false;

        SignedJWT jwt = parseToken(token);

        if(jwt != null && _cert != null)
         return verifySignature(jwt, _cert) && validateAudience(jwt) && validateIssuer(jwt);

        return false;
    }

    private SignedJWT parseToken(String token)
    {
        try {
            return SignedJWT.parse(token);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Document getFedMetaDataDocument()
    {
        try {
            BufferedInputStream in = new BufferedInputStream(new URL(fedEndpoint).openStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String content = "";
            for (String line; (line = reader.readLine()) != null; ) {
                content += line;
            }

            DocumentBuilderFactory domFactory = DocumentBuilderFactory
                    .newInstance();
            domFactory.setNamespaceAware(true);
            DocumentBuilder builder = domFactory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(content.trim().replaceFirst("^([\\W]+)<", "<"))));
            doc.normalizeDocument();

            return doc;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private X509Certificate getCert(Document doc)
    {
        try {
            NodeList nodeList = doc.getElementsByTagName("RoleDescriptor");

            String certificate = null;

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            xpath.setNamespaceContext(new NsHelper.AzureAdNamespaceContext(null));
            XPathExpression expr = xpath.compile("//*[local-name() = 'RoleDescriptor'][@xsi:type='fed:SecurityTokenServiceType']//*[local-name() = 'X509Certificate'][1]");

            NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            certificate =  nodes.item(0).getTextContent();

            if(certificate != null && certificate.length() > 0) {
                byte[] decoded = Base64.decode(certificate);
                System.out.println(decoded);
                return (X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(decoded));
            }

            return null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private boolean verifySignature(SignedJWT jwt, X509Certificate cert)
    {
        try {
            RSASSAVerifier verifier = new RSASSAVerifier((java.security.interfaces.RSAPublicKey) cert.getPublicKey());
            return jwt.verify(verifier);
        }
        catch(JOSEException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private boolean validateAudience(SignedJWT jwt)
    {
        return audience.equals(jwt.getPayload().toJSONObject().get("aud"));
    }

    private boolean validateIssuer(SignedJWT jwt)
    {
        return issuer.equals(jwt.getPayload().toJSONObject().get("iss"));
    }
}
