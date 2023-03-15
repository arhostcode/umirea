package ru.ardyc.utils;

public class RequestUtils {

    public static boolean isInvalid(Object... data) {
        for (Object d : data) {
            if (d == null)
                return true;
        }
        return false;
    }
}
