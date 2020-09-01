package com.nouks.devotion.testUtils.jwtengine;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;

public class JwtKeyStore {

    private static final String PRIVATE_KEY_STORE = "src/test/java/com/mungwincore/authresource/testUtils/KeyPair/mgtestkeystore.jks";

    public static PrivateKey getPrivateKey() throws Exception {
        File file = new File(PRIVATE_KEY_STORE);
        InputStream inputStream = new FileInputStream(file);
        KeyStore keyStore = KeyStore.getInstance("JCEKS");
        keyStore.load(inputStream, "mgtest@1".toCharArray());
        KeyStore.PasswordProtection keyPassword = new KeyStore.PasswordProtection("mgtest@1".toCharArray());
        KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry("servercert", keyPassword);
        return privateKeyEntry.getPrivateKey();
    }
}
