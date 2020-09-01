package com.nouks.devotion.testUtils.jwtengine;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

/**
 * author nouks
 */
@Service(value = "testJwt")
public class JWTServiceImpl implements JWTService{
    @Override
    public String generateSignedToken(Map<String, Object> payLoad) {

        JwtToken jwtToken = new JwtToken();
        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        jwtToken.setHeader(header);
        if (payLoad == null) payLoad = new HashMap<>();
        Long iat = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(1));
        Long exp = iat + 3600L;
        payLoad.put("exp", exp);
        payLoad.put("user_id", 2);
        payLoad.put("user_name", "admin@devotion.com");
        payLoad.put("scope", Arrays.asList("read", "write", "trust"));
        payLoad.put("jti",  UUID.randomUUID().toString());
        payLoad.put("client_id", "devotion_client");
        jwtToken.setPayload(payLoad);
        JwtGenerator jwtGenerator = new JwtGenerator();
        return jwtGenerator.generateToken(jwtToken);
    }
}
