package com.artemobrazumov.shorty.short_url.factory;

import java.security.SecureRandom;

public class ShortUrlStringGenerator {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    private final int length;

    public ShortUrlStringGenerator(int length) {
        this.length = length;
    }

    public String generateRandomString() {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(index));
        }
        return sb.toString();
    }
}
