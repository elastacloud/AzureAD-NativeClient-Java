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
        // refer to https://github.com/azureadsamples/nativeclient-dotnet for how to generate
        // a bearer token, then use this token at http://localhost:8080/secure/passive
        // view ~/sample/*.saz for samples of how this works
    }
}
