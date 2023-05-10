package ru.ardyc.utils;

import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static Map<String, String> parseMap(String content) throws Exception {
        Map<String, String> map = new HashMap<>();
        try {
            String[] contentDisposition = content.split(";");
            for (String element : contentDisposition) {
                String c = element.split("=")[element.split("=").length - 1];
                map.put(element.trim(), c.replaceAll("\"", ""));
            }
        } catch (Exception e) {
            return null;
        }
        return map;
    }

}
