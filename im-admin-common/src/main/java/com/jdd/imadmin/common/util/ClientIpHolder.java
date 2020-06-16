package com.jdd.imadmin.common.util;


import com.alibaba.ttl.TransmittableThreadLocal;

public class ClientIpHolder {
    private static final ThreadLocal<String> clientIpLocal = new TransmittableThreadLocal<String>();

    public static void set(String val) {
        clientIpLocal.set(val);
    }

    public static String get() {
        return clientIpLocal.get();
    }

    public static void clear() {
        clientIpLocal.remove();
    }
}
