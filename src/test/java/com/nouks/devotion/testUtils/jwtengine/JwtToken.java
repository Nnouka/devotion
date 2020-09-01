package com.nouks.devotion.testUtils.jwtengine;

import lombok.Data;

import java.util.Map;
@Data
public class JwtToken {
    private Map<String, Object> payload;
    private Map<String, Object> header;
}
