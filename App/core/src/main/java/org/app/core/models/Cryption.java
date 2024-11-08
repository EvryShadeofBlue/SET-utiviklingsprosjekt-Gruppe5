package org.app.core.models;

import org.web3j.crypto.*;
import org.web3j.utils.Numeric;

import java.nio.charset.StandardCharsets;

public class Cryption {
    private static final String salt = "0x54hg3v52hg45f454gh23g4f2";

    public static String hashPasswordWithSalt(String password) {
        String saltedPassword = password + salt;
        byte[] hashedBytes = Hash.sha3(saltedPassword.getBytes(StandardCharsets.UTF_8));
        return Numeric.toHexString(hashedBytes);
    }
}