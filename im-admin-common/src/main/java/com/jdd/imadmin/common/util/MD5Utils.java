package com.jdd.imadmin.common.util;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Arrays;
import java.util.List;

public class MD5Utils {

    public static String encrypt(String input, String key) {
        input = cleanSpecial(input);
        return DigestUtils.md5Hex(input + key).toUpperCase();
    }

    public static String encrypt(String input) {
        return encrypt(input, "");
    }

    public static String cleanSpecial(String word) {
        String[] special = new String[]{";--", "'", "=", " * ", "!", "<", ">", "!", "/", " and ", " union ", " select ", " from ", " order ", " by ", " update ", " insert ", " waitfor", " delay ", " exec ", "<strong>", "</strong>"};
        List<String> specialWords = Arrays.asList(special);
        for (String item : specialWords) {
            word = word.replace(item, "").replace(item.toUpperCase(), "");
        }
        return word;
    }
}
