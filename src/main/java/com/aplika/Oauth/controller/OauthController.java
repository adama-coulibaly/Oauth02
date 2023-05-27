package com.aplika.Oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

@RestController
public class OauthController {
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @RequestMapping("/**")
    private StringBuffer getOauth2LoginInfo(Principal user){

        StringBuffer protectedInfo = new StringBuffer();

        OAuth2AuthenticationToken authToken = ((OAuth2AuthenticationToken) user);
        OAuth2AuthorizedClient authClient =
                this.authorizedClientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), authToken.getName());
        if(authToken.isAuthenticated()){

            Map<String,Object> userAttributes = ((DefaultOAuth2User) authToken.getPrincipal()).getAttributes();

            String userToken = authClient.getAccessToken().getTokenValue();
//            String userRefresToken = authClient.getRefreshToken().getTokenValue();
            protectedInfo.append("Bienvenue, " + userAttributes.get("login")+"<br><br>");
            protectedInfo.append("e-mail: " + userAttributes.get("email")+"<br><br>");

            protectedInfo.append("Nom et prenom: " + userAttributes.get("login")+"<br><br>");
            protectedInfo.append("Avatar: " + userAttributes.get("avatar_url")+"<br><br>");


            protectedInfo.append("Access Token: " + userToken+"<br><br>");
            protectedInfo.append("Refresh Token: " + userAttributes+"<br><br>");
        }
        else{
            protectedInfo.append("NA");
        }
        return protectedInfo;
    }
}
