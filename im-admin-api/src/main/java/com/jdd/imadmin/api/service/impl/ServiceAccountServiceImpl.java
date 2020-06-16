package com.jdd.imadmin.api.service.impl;

import cn.wildfirechat.common.ErrorCode;
import cn.wildfirechat.sdk.model.IMResult;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jdd.im.sdk.MerchantAdmin;
import com.jdd.im.sdk.ServiceAdmin;
import com.jdd.im.sdk.model.PageResult;
import com.jdd.im.sdk.model.QueryWatchPageResult;
import com.jdd.imadmin.api.common.BasePageModel;
import com.jdd.imadmin.api.common.ResultModel;
import com.jdd.imadmin.api.controller.param.*;
import com.jdd.imadmin.api.converter.AutoReplyConverter;
import com.jdd.imadmin.api.converter.UserInfoConverter;
import com.jdd.imadmin.api.handler.ServiceAccountPushHandler;
import com.jdd.imadmin.api.service.ServiceAccountService;
import com.jdd.imadmin.api.service.TokenVerifyService;
import com.jdd.imadmin.api.service.dto.*;
import com.jdd.imadmin.common.enums.ApiErrorCode;
import com.jdd.imadmin.common.enums.StatusEnum;
import com.jdd.imadmin.common.util.QrCodeUtils;
import com.jdd.imadmin.dao.entity.ImAutoReply;
import com.jdd.imadmin.dao.entity.Oauth2Token;
import com.jdd.imadmin.dao.mapper.ImAutoReplyMapper;
import com.jdd.imadmin.dao.mapper.ImMessageTemplateMapper;
import com.jdd.imadmin.service.auth2.OAuth2UserService;
import com.jdd.imserver.pojos.QueryWatchListRequest;
import com.jdd.imserver.pojos.ServiceAccount;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author @sailength on 2020/2/20.
 *         服务号对外门户类
 */
@Service
public class ServiceAccountServiceImpl implements ServiceAccountService, BeanPostProcessor {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserInfoConverter userInfoMapper;


    @Autowired
    private ImAutoReplyMapper imAutoReplyMapper;

    @Autowired
    private AutoReplyConverter autoReplyConverter;

    @Autowired
    private ImMessageTemplateMapper messageTemplateMapper;

    @Autowired
    private OAuth2UserService oAuth2UserService;

    @Autowired
    private TokenVerifyService verifyService;

    private Map<Integer, ServiceAccountPushHandler> serviceAccountPushHandlerMap = new HashMap<>();

    /**
     * 查询服务号关注列表
     */
    @Override
    public ResultModel<BasePageModel<UserInfoDto>> queryWatchList(QueryWatchListDto queryWatchListDto) {
        Oauth2Token oauth2Token = verifyService.getTokenDetail(queryWatchListDto.getToken());
        if (oauth2Token == null) {
            return ResultModel.accessTokenInvalid();
        }
        QueryWatchListRequest queryWatchListRequest = new QueryWatchListRequest();
        BeanUtils.copyProperties(queryWatchListDto, queryWatchListRequest);
        try {
               IMResult<PageResult<QueryWatchPageResult>> imResult = ServiceAdmin.queryWatchList(queryWatchListRequest);
            if (imResult.getCode() == ErrorCode.ERROR_CODE_SUCCESS.getCode() && imResult.getResult() != null && imResult.getResult().getList() != null) {
                BasePageModel<UserInfoDto> basePageModel = new BasePageModel<>();
                PageResult<QueryWatchPageResult> pageResultPageResult = imResult.getResult();
                basePageModel.setTotal(pageResultPageResult.getTotal());
                basePageModel.setPageSize(pageResultPageResult.getPageSize());
                basePageModel.setPageNo(pageResultPageResult.getPageNo());
                basePageModel.setData(wrapUserInfo(oauth2Token.getClientId(), imResult.getResult().getList()));
                return ResultModel.newSuccessModel(basePageModel);
            } else {
                logger.error("接口返回异常，异常信息为{}", imResult.getMsg());
                return ResultModel.newErrorModel();
            }
        } catch (Exception e) {
            logger.error("调用接口异常", e);
            return ResultModel.newErrorModel();
        }
    }


    private List<UserInfoDto> wrapUserInfo(String clientId, List<QueryWatchPageResult> watchList) {
        List<UserInfoDto> list = new ArrayList<>();
        for (QueryWatchPageResult queryWatchPageResult : watchList) {
            UserInfoDto userInfoDto = new UserInfoDto();
            userInfoDto.setHeadPic(queryWatchPageResult.getHeadPic());
            userInfoDto.setOpenId(oAuth2UserService.generateOpenId(queryWatchPageResult.getUid(), clientId));
            userInfoDto.setNickName(queryWatchPageResult.getNickName());
            list.add(userInfoDto);
        }
        return list;
    }

    /**
     * 为某个服务号添加自动回复
     */
    @Override
    public ResultModel<Integer> addAutoReply(AddAutoReplyDto addAutoReplyDto) {
        List<ImAutoReply> list = imAutoReplyMapper.queryAutoReplyByKeyWord(addAutoReplyDto.getKeyword(), addAutoReplyDto.getServiceOpenId());
        if (CollectionUtils.isEmpty(list)) {
            ImAutoReply imAutoReply = new ImAutoReply();
            imAutoReply.setServiceOpenId(addAutoReplyDto.getServiceOpenId());
            imAutoReply.setContent(addAutoReplyDto.getContent());
            imAutoReply.setKeyword(addAutoReplyDto.getKeyword());
            imAutoReply.setContentType(addAutoReplyDto.getContentType());
            imAutoReply.setStatus(StatusEnum.VALID.getValue());
            imAutoReply.setCreateTime(new Date());
            imAutoReply.setUpdateTime(new Date());
            imAutoReply.setCreateBy("system");
            int id = imAutoReplyMapper.insert(imAutoReply);
            return ResultModel.newSuccessModel(id);
        } else {
            return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_KEYWORD_REPETITION);
        }

    }

    /**
     * 查询某个服务号的自动回复设置
     */
    @Override
    public ResultModel<BasePageModel<ReplyInfoDto>> queryAutoReply(QueryAutoReplyDto queryAutoReplyDto) {
        String seriviceOpenId = queryAutoReplyDto.getServiceOpenId();
        Assert.notNull(seriviceOpenId, "seriviceOpenId cant be null");
        Page page = PageHelper.startPage(queryAutoReplyDto.getPageNo(), queryAutoReplyDto.getPageSize());
        List<ImAutoReply> list = imAutoReplyMapper.queryAutoReplyBySeriviceId(seriviceOpenId);
        BasePageModel basePageModel = BasePageModel.build(page.getPageNum(), page.getPageSize(), page.getTotal(), autoReplyConverter.dos2dtos(list));
        return ResultModel.newSuccessModel(basePageModel);
    }

    /**
     * 删除某个服务号的自动回复
     */
    @Override
    public ResultModel deleteAutoReply(DeleteAutoReplyDto deleteAutoReplyDto) {
        Assert.notNull(deleteAutoReplyDto.getServiceOpenId(), "serviceId cant be null");
        Example example = new Example(ImAutoReply.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serviceOpenId", deleteAutoReplyDto.getServiceOpenId());
        criteria.andEqualTo("id", deleteAutoReplyDto.getReplyId());
        List<ImAutoReply> autoReplies = imAutoReplyMapper.selectByExample(example);

        if (autoReplies != null && autoReplies.size() > 0) {
            imAutoReplyMapper.deleteAutoReplyByServiceId(deleteAutoReplyDto.getServiceOpenId(), deleteAutoReplyDto.getReplyId());
            return ResultModel.newSuccessModel();
        } else {
            return ResultModel.parameterAbnormality("自动回复编号有误！");
        }

    }

    @Override
    public ResultModel updateAutoReply(UpdateAutoReplyDto updateAutoReplyDto) {
        Example example = new Example(ImAutoReply.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serviceOpenId", updateAutoReplyDto.getServiceOpenId());
        criteria.andEqualTo("keyword", updateAutoReplyDto.getKeyword());
        List<ImAutoReply> imAutoReplies = imAutoReplyMapper.selectByExample(example);
        if (imAutoReplies != null) {
            ImAutoReply imAutoReply = imAutoReplies.get(0);
            imAutoReply.setContent(updateAutoReplyDto.getContent());
            imAutoReply.setContentType(updateAutoReplyDto.getContentType());
            imAutoReply.setStatus(updateAutoReplyDto.getStatus());
            imAutoReply.setUpdateTime(new Date());
            imAutoReplyMapper.updateByPrimaryKeySelective(imAutoReply);
            return ResultModel.newSuccessModel();
        } else {
            return ResultModel.parameterAbnormality("关键字不存在！");
        }
    }


    /**
     * 创建服务号
     *
     * @param param
     * @return
     */
    @Override
    public ResultModel createServiceAccount(ServiceAccountParam param) {
        ServiceAccount serviceAccount = new ServiceAccount();
        BeanUtils.copyProperties(param, serviceAccount);

        serviceAccount.setStatus(StatusEnum.VALID.getValue());
        if (param.getMenuJson() != null && !CollectionUtils.isEmpty(param.getMenuJson().getButton())) {
            List<ServiceMenuParam> serviceMenuParamList = param.getMenuJson().getButton();
            serviceMenuParamList.forEach(e -> e.setId(UUID.randomUUID().toString()));
            serviceAccount.setMenuJson(JSONObject.toJSONString(param.getMenuJson()));
        }
        serviceAccount.setCreateTime(new Date());
        serviceAccount.setUpdateTime(new Date());
        serviceAccount.setCreateBy("");
        serviceAccount.setUpdateBy("");

        try {
            IMResult<String> saveResult = ServiceAdmin.createServiceAccount(serviceAccount);
            if (saveResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                Map<String, String> map = new HashMap<>();
                map.put("merchant_open_id", param.getMerchantOpenId());
                map.put("service_open_id", saveResult.getResult());
                return ResultModel.newSuccessModel(map);
            }  else if (saveResult.getErrorCode() == ErrorCode.ERROR_CODE_SERVICE_ACCOUNT_NAME_ALREADY_EXIST) {
                return ResultModel.newErrorModel(ApiErrorCode.ERROR_CODE_SERVICE_ACCOUNT_NAME_ALREADY_EXIST);
            } else {
                return ResultModel.parameterAbnormality("创建失败："+saveResult.msg);
            }

        } catch (Exception e) {
            logger.error("saveMerchant,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }


    @Override
    public ResultModel updateServiceAccount(UpdateServiceAccountParam param) {
        ServiceAccount serviceAccount = new ServiceAccount();
        BeanUtils.copyProperties(param, serviceAccount);
        if (param.getMenuJson() == null) {
            serviceAccount.setMenuJson(null);
        } else {
            serviceAccount.setMenuJson(JSONObject.toJSONString(param.getMenuJson()));
        }
        serviceAccount.setUpdateTime(new Date());

        try {
            IMResult<String> saveResult = ServiceAdmin.updateServiceAccount(serviceAccount);
            if (saveResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                return ResultModel.newSuccessModel();
            } else {
                return ResultModel.parameterAbnormality("修改失败");
            }
        } catch (Exception e) {
            logger.error("saveMerchant,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }


    /**
     * 取商户的服务号列表
     *
     * @param param
     * @return
     */
    @Override
    public ResultModel listServiceAccount(QueryServiceAccountParam param) {
        try {
            IMResult<List<ServiceAccount>> saveResult = MerchantAdmin.listMerchantService(param.getMerchantOpenId());
            if (saveResult.getErrorCode() == ErrorCode.ERROR_CODE_SUCCESS) {
                List<ServiceAccount> result = saveResult.getResult();
                String json = new Gson().toJson(result);
                List<ServiceAccount> serviceAccounts = new Gson().fromJson(json, new TypeToken<List<ServiceAccount>>() {
                }.getType());

                List<ServiceAccountDto> serviceAccountDtos = serviceAccounts.stream().filter(Objects::nonNull).map(serviceAccount -> {
                    ServiceAccountDto serviceAccountDto = new ServiceAccountDto();
                    serviceAccountDto.setServiceOpenId(serviceAccount.getServiceOpenId());
                    serviceAccountDto.setMerchantOpenId(serviceAccount.getMerchantOpenId());
                    serviceAccountDto.setName(serviceAccount.getName());
                    serviceAccountDto.setHeadPic(serviceAccount.getHeadPic());
                    serviceAccountDto.setWelcome(serviceAccount.getWelcome());
                    serviceAccountDto.setDescription(serviceAccount.getDescription());
                    if (StringUtils.isNotBlank(serviceAccount.getMenuJson())) {
                        serviceAccountDto.setMenuJson(JSONObject.parseObject(serviceAccount.getMenuJson(),
                                ServiceMenuListParam.class));
                    }
                    return serviceAccountDto;
                }).collect(Collectors.toList());
                return ResultModel.newSuccessModel(serviceAccountDtos);
            } else {
                return ResultModel.parameterAbnormality("获取失败");
            }
        } catch (Exception e) {
            logger.error("listServiceAccount,异常={}", ExceptionUtils.getStackTrace(e));
            return ResultModel.newErrorModel();
        }
    }


    /**
     * 给某个服务号某个用户发送订阅消息
     *
     * @param param
     * @return
     */
    @Override
    public ResultModel sendMessage(MessageParam param) {
        ServiceAccountPushHandler serviceAccountPushHandler = serviceAccountPushHandlerMap.get(param.getMessageType());
        if (serviceAccountPushHandler == null) {
            return ResultModel.parameterAbnormality();
        }
        return serviceAccountPushHandler.excuteSend(param);
    }

    @Override
    public ResultModel<String> getQrCode(GetServiceAccountQRCodeParam param) {
        try {
            return ResultModel.newSuccessModel(QrCodeUtils.getServiceQr(param.getServiceId()));
        } catch (Exception e) {
            logger.error("get qr error,param {}", e, param.toString());
            return ResultModel.newErrorModel(e.getMessage());
        }
    }

    private String replacePlaceholder(String template, Map<String, Object> map) {
        if (map != null && !map.isEmpty()) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                template = template.replace("{" + entry.getKey() + "}", entry.getValue().toString());
            }
        }
        return template;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ServiceAccountPushHandler) {
            ServiceAccountPushHandler handler = ((ServiceAccountPushHandler) bean);
            serviceAccountPushHandlerMap.put(handler.getType().getValue(), handler);
        }
        return bean;
    }

}
