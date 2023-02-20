package ru.ardyc.utils;

import java.util.Random;

public class VerificationUtils {

    public static String createVerificationCode(String userToken) {
        Random seedRandomCodeGenerator = new Random(stringToSeed(userToken + (System.getenv("SALT") == null ? "" : System.getenv("SALT"))));
        int firstNum = seedRandomCodeGenerator.nextInt(10);
        int secondNum = seedRandomCodeGenerator.nextInt(10);
        int thirdNum = seedRandomCodeGenerator.nextInt(10);
        int fourthNum = seedRandomCodeGenerator.nextInt(10);
        return String.valueOf(firstNum) + String.valueOf(secondNum) + String.valueOf(thirdNum) + String.valueOf(fourthNum);
    }


    private static long stringToSeed(String s) {
        if (s == null) {
            return 0;
        }
        long hash = 0;
        for (char c : s.toCharArray()) {
            hash = 31L * hash + c;
        }
        return hash;
    }
}
