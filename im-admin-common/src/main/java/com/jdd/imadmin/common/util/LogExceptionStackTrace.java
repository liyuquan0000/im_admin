package com.jdd.imadmin.common.util;

import lombok.Cleanup;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogExceptionStackTrace {
    public static Object erroStackTrace(Object obj){
        if (obj instanceof Exception) {
            try {
                Exception eObj = (Exception) obj;
                @Cleanup StringWriter sw = new StringWriter();
                @Cleanup PrintWriter pw = new PrintWriter(sw);
                String exceptionStack = "\r\n";
                eObj.printStackTrace(pw);
                exceptionStack = sw.toString();
                return exceptionStack;
            } catch (Exception e) {
                e.printStackTrace();
                return obj;
            }
        } else {
            return obj;
        }
    }
}
