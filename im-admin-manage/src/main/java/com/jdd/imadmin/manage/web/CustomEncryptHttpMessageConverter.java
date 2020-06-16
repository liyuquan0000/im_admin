package com.jdd.imadmin.manage.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.jdd.imadmin.common.exception.AesException;
import com.jdd.imadmin.common.util.TransferAesEncrypt;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;


public class CustomEncryptHttpMessageConverter extends FastJsonHttpMessageConverter {

    public static final String aesKey = "d3YmI1BUOSE2S2YmalBVZUQ=";

    public CustomEncryptHttpMessageConverter() {
        super();
    }

    @Override
    public Object read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        if(EncryptLocal.get() != null && EncryptLocal.get()){
            return readType(getType(type, contextClass), inputMessage);
        }else{
            return super.read(type,contextClass, inputMessage);
        }

    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        if(EncryptLocal.get() != null && EncryptLocal.get()){
            return readType(getType(clazz, null), inputMessage);
        }else{
            return super.readInternal(clazz, inputMessage);
        }
    }

    private Object readType(Type type, HttpInputMessage inputMessage) {
        try {
            String requestString = IOUtils.toString(inputMessage.getBody(), Charset.forName("UTF-8"));
            String jsonStr = TransferAesEncrypt.aesDecrypt(requestString, aesKey, "utf-8");
            return JSON.parseObject(jsonStr, type, super.getFastJsonConfig().getFeatures());
        } catch (JSONException ex) {
            throw new HttpMessageNotReadableException("JSON parse error: " + ex.getMessage(), ex);
        } catch (IOException ex) {
            throw new HttpMessageNotReadableException("I/O error while reading input message", ex);
        } catch (AesException ex) {
            throw new HttpMessageNotReadableException("Decrypt error:", ex);
        }
    }
}
