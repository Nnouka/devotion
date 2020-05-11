package com.nouks.devotion.security.services;


import com.nouks.devotion.domain.models.User;
import com.nouks.devotion.domain.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class JwtTokenEnhancer implements TokenEnhancer {
    private Logger logger = LoggerFactory.getLogger(JwtTokenEnhancer.class);

    private UserRepository userRepository;

    @Autowired
    public JwtTokenEnhancer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        try {
            Optional<User> optionalUser = userRepository.findFirstByEmail(oAuth2Authentication.getName());
            optionalUser.ifPresent(value -> additionalInfo.put("user_id", value.getId()));
        }catch (Exception ex){
            logger.warn(ex.getMessage());
        }

        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
