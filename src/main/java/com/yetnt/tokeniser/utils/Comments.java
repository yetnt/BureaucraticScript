package com.yetnt.tokeniser.utils;

import com.yetnt.lang.Chars;
import org.jetbrains.annotations.NotNull;

public class Comments {
    @NotNull
    public static String decimate(@NotNull String line) {
        if (line.startsWith(Chars.COMMENT)) return "";
        int commentIndex = line.indexOf(Chars.COMMENT);
        if (commentIndex != -1)
            return line.substring(0, commentIndex).trim();

        return line.trim();
    }
}
