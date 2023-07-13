package utils;


import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {


    private static volatile String secretKey;

    private static KeyGenerator instance;

    private KeyGenerator() {
    }

    public String getSecretKey () {
        if (secretKey == null) {
            synchronized (KeyGenerator.class) {
                if (secretKey == null) {
                    SecureRandom secureRandom = new SecureRandom();
                    byte[] keyBytes = new byte[32];
                    secureRandom.nextBytes(keyBytes);
                    secretKey =  Base64.getEncoder().encodeToString(keyBytes);
                }
            }
        }
        return secretKey;
    }

    public static KeyGenerator getInstance() {
        if (instance == null) {
            synchronized (KeyGenerator.class) {
                if (instance == null) {
                    instance = new KeyGenerator();
                }
            }
        }
        return instance;
    }
}
