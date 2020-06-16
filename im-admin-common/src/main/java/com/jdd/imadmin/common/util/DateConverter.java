/**
 * LY.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.jdd.imadmin.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateConverter {

    /**
     * DateToString
     * @param date
     * @return
     */
    public String asString(Date date) {
        return date != null ? new SimpleDateFormat(DateUtil.newFormat).format(date) : null;
    }

    /**
     * StringToDate
     * @param date
     * @return
     */
    public Date asDate(String date) {
        try {
            if ("1900-01-01".equals(date))
                return new SimpleDateFormat(DateUtil.webFormat).parse(date);
            else
                return StringUtils.isNotBlank(date) ? new SimpleDateFormat(DateUtil.newFormat).parse(date) : null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
