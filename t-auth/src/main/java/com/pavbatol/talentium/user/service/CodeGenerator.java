package com.pavbatol.talentium.user.service;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class CodeGenerator {

    private static final Charset CHARSET = StandardCharsets.UTF_8;
    public static final int LENGTH = 64;

    public static String generateCode() {
        return generateCode(true);
    }

    public static String generateCodeWithoutSpecial() {
        return generateCode(false);
    }

    public static String encode(String code) {
        return new String(Base64.getEncoder().encode(code.getBytes(CHARSET)));
    }

    public static String decode(String encodedCode) {
        return new String(Base64.getDecoder().decode(encodedCode), CHARSET);
    }

    private static String generateCode(boolean withSpecial) {
        final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String lower = "abcdefghijklmnopqrstuvwxyz";
        final String digits = "0123456789";
        final String special = "!@#$%^&*()_+-=[]{};:,.<>?";
        final String all = upper + lower + digits + (withSpecial ? special : "");
        final Random random = new Random();
        final StringBuilder code = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(all.length());
            code.append(all.charAt(index));
        }
        return code.toString();
    }
}
