package com.jdd.imadmin.api.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author @sailength on 2020/2/20.
 *         分页基础对象
 */
@Data
public class BasePageModel<T> implements Serializable {

    private Long total;

    private Integer pageNo;

    private Integer pageSize;

    private List<T> data;


    public static <T> BasePageModel<T> build(Integer pageNo, Integer pageSize, Long total, List<T> data) {
        BasePageModel basePageModel = new BasePageModel();
        basePageModel.setData(data);
        basePageModel.setPageNo(pageNo);
        basePageModel.setPageSize(pageSize);
        basePageModel.setTotal(total);
        return basePageModel;
    }


}
