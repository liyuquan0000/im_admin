package com.jdd.imadmin.api.service;

import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.VersionParam;

public interface VersionService {

    ResultModel getVersionDetail(VersionParam param);

}
