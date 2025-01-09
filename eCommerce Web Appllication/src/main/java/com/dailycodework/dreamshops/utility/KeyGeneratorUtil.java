package com.dailycodework.dreamshops.utility;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.ECGenParameterSpec;

public class KeyGeneratorUtil {
    public static KeyPair generateECKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
        keyPairGenerator.initialize(ecSpec);
        return keyPairGenerator.generateKeyPair();
    }
}

