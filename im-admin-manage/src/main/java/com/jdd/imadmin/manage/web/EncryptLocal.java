package com.jdd.imadmin.manage.web;

public class EncryptLocal {
    private static ThreadLocal<Boolean> encryptLocal = new ThreadLocal<>();

    public static void put(Boolean encryptValue) {
        encryptLocal.remove();
        encryptLocal.set(encryptValue);
    }

    public static Boolean get() {
        return encryptLocal.get();
    }

    public static void clear() {
        encryptLocal.remove();
    }
}
