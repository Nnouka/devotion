package com.nouks.devotion.testUtils;


import com.nouks.devotion.testUtils.jwtengine.JWTService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AutoBonusJwtTest {
    @Autowired
    private JWTService jwtService;
    Logger logger = LoggerFactory.getLogger(AutoBonusJwtTest.class);

    @Test
    public void generate_a_token() {
        Map<String, Object> payLoad = new HashMap<>();
        payLoad.put("authorities", Arrays.asList("USER", "ADMIN"));
        String jwtToken = jwtService.generateSignedToken(payLoad);
        logger.info("AutoBonus Jwt: {}", jwtToken);
    }

}
