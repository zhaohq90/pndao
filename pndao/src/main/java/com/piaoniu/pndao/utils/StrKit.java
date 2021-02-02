package com.piaoniu.pndao.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrKit {

    /**
     * 驼峰命名转换为蛇形命名
     */
    public static String humpToLine(String str) {
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

}
