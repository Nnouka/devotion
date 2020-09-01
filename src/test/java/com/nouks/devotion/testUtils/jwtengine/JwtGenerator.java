package com.nouks.devotion.testUtils.jwtengine;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.PrivateKey;
import java.util.Map;

public class JwtGenerator {
    public String generateToken(JwtToken jwtToken){
        String token = null;

        Map<String, Object> header;
        Map<String, Object> payLoad;
        try {
            payLoad = jwtToken.getPayload();
            header = jwtToken.getHeader();
//            PrivateKey privateKey = JwtKeyStore.getPrivateKey();
//            String privateKey = "sseaefcx2rsyst08#123456789jsjiwyuey738wkaj983jusjnaouoe=-0s9jsknenj3k4deds";
            String privateKey = "uwo4nso[ksk{ksjsjmmen328hel{p837JKdevotiong@3$#slnn8usksmaljfoeo0SSSodncbcCSSaigeNXWhI3ihsi";
            token = Jwts.builder()
                    .setClaims(payLoad)
                    .setHeader(header)
                    .signWith(SignatureAlgorithm.HS256, privateKey).compact();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }
}
