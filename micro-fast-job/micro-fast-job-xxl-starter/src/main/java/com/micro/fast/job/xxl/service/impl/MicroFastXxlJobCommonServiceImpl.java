package com.micro.fast.job.xxl.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.micro.fast.job.xxl.constant.MicroFastIJobHandlerConstant;
import com.micro.fast.job.xxl.service.MicroFastXxlJobCommonService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author shoufeng
 */
@Service
@Slf4j
public class MicroFastXxlJobCommonServiceImpl implements MicroFastXxlJobCommonService {

    @Resource
    private ApplicationContext applicationContext;

    @XxlJob(value = "microFastIJobHandler")
    @Override
    public ReturnT<String> microFastIJobHandler(String params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        JSONObject paramsJs = JSON.parseObject(params);
        String beanName = paramsJs.getString(MicroFastIJobHandlerConstant.BEAN_NAME);
        Object bean = applicationContext.getBean(beanName);
        String methodName = paramsJs.getString(MicroFastIJobHandlerConstant.METHOD);
        String methodParam = paramsJs.getString(MicroFastIJobHandlerConstant.METHOD_PARAM);
        Method method = StringUtils.isEmpty(methodParam) ? bean.getClass().getDeclaredMethod(methodName) : bean.getClass().getDeclaredMethod(methodName, String.class);
        Object methodInvokeResult = StringUtils.isEmpty(methodParam) ? method.invoke(bean) : method.invoke(bean, methodParam);

        ReturnT<String> returnT = ReturnT.SUCCESS;
        returnT.setContent(JSON.toJSONString(methodInvokeResult));
        return returnT;
    }

    @Override
    public void init() {
        log.info("调度任务fastIJobHandler初始化成功");
    }

    @Override
    public void destroy() {
        log.info("调度任务fastIJobHandler销毁成功");
    }
}
