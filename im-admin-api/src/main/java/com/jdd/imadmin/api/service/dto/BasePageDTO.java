package com.jdd.imadmin.api.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author @sailength on 2020/2/20.
 */
@Data
public class BasePageDTO implements Serializable {

    private static final long serialVersionUID = -8881540761352140303L;

    @NotNull(message="pageNo cant be null")
    @JSONField(name = "page_no")
    private Integer pageNo=1;
    @NotNull(message="pageSize cant be null")
    @JSONField(name = "page_size")
    private Integer  pageSize=10;


}
