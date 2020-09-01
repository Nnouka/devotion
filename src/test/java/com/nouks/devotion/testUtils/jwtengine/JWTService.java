package com.nouks.devotion.testUtils.jwtengine;

import java.util.Map;

public interface JWTService {
    String generateSignedToken(Map<String, Object> payLoad); // add parameter Map<String, Object> payload
}
