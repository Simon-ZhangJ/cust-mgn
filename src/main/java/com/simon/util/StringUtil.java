package com.simon.util;

public class StringUtil {

    public static boolean isEmpty(String input) {
        return (input == null || input.isEmpty());
    }

    public static boolean isNotEmpty(String input) {
        return !StringUtil.isEmpty(input);
    }

    public static String parseNullString(String input) {
        return (input == null) ? "" : input;
    }
}
