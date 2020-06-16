package com.jdd.imadmin.manage.controller.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by wjd on 2019/4/24.
 */
@Data
@ApiModel
public class PageParam {

    //页数
    @ApiModelProperty(value="第几页", required = true)
    private int pageNum;

    // 每页的数量
    @ApiModelProperty(value="每页个数", required = true)
    private int pageSize;

}
