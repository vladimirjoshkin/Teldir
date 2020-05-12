package com.teldir.core;

public class StringUtils {
    public static String deleteDoubleSpaces(String str) {
        while(str.contains("  ")) {
            str = str.replace("  ", " ");
        }
        return str;
    }
}