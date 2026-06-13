package com.yetnt.tokenzier.utils;

import com.yetnt.lang.Chars;

public class Comments {
    public static String decimate(String line) {
        if (line.startsWith(Chars.COMMENT)) return "";
        int commentIndex = line.indexOf(Chars.COMMENT);
        if (commentIndex != -1)
            return line.substring(0, commentIndex).trim();

        return line.trim();
    }
}
