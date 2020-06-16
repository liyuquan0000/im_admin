package com.jdd.imadmin.api.handler.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author @sailength on 2020/3/26.
 */
@Data
public class HrefContent implements Serializable {

    private static final long serialVersionUID = -193389121693136235L;

    private String url;

    private String content;
}
