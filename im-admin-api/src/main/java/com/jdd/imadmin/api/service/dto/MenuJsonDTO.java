package com.jdd.imadmin.api.service.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author @sailength on 2020/3/30.
 */
@Data
public class MenuJsonDTO implements Serializable {

    private static final long serialVersionUID = -7878748211046746810L;

    private String id;

    private Integer type;

    private String value;

    private String name;
}
