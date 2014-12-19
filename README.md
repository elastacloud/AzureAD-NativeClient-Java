NativeClient-Java
===================

This sample demonstrates a .Net WPF application calling a web API written in Java that is secured using Azure AD. The .Net application uses the Active Directory Authentication Library (ADAL) to obtain a JWT access token through the OAuth 2.0 protocol. The access token is sent to the web API to authenticate the user. The web API uses Adal4J to validate the token.

For more information about how the protocols work in this scenario and other scenarios, see [Authentication Scenarios for Azure AD](http://go.microsoft.com/fwlink/?LinkId=394414). 

Refer to [https://github.com/azureadsamples/nativeclient-dotnet](https://github.com/azureadsamples/nativeclient-dotnet) for how to generate a bearer token, then use this token at http://localhost:8080/secure/passive.

View ~/sample/*.saz for samples of how this works

