package com.jdd.imadmin.common.util;

import com.alibaba.ttl.TransmittableThreadLocal;

public class TraceIdHolder {

	private static final ThreadLocal<String> traceIdHolder = new TransmittableThreadLocal<String>();

	public static void set(String val) {
		traceIdHolder.set(val);
	}

	public static String get() {
	        return traceIdHolder.get();
	    }  
	      
	public static void clear() {
		traceIdHolder.remove();
	}
}
