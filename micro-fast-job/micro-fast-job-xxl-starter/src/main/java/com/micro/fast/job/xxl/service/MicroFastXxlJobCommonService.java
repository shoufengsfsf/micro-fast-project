package com.micro.fast.job.xxl.service;

import com.xxl.job.core.biz.model.ReturnT;

import java.lang.reflect.InvocationTargetException;

/**
 * 公共服务
 *
 * @author shoufeng
 */
public interface MicroFastXxlJobCommonService {

    /**
     * 公共调度任务（根据beanName、method、methodParam（目前只支持调用无参或者单个字符串参数的方法）调用调度器内具体方法）
     *
     * @param params {"method":"demoJobHandler","beanName":"sampleXxlJob","methodParam":"kkk"}
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    ReturnT<String> microFastIJobHandler(String params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    /**
     * 初始化调度任务
     */
    void init();

    /**
     * 销毁调度任务
     */
    void destroy();


}
