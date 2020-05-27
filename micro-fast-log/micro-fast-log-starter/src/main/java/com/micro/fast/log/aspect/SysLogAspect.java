package com.micro.fast.log.aspect;

import com.alibaba.fastjson.JSON;
import com.micro.fast.log.annotation.SysLog;
import com.micro.fast.log.dto.SysLogDto;
import com.micro.fast.log.dto.SysUserInfoDto;
import com.micro.fast.log.listener.SysLogEvent;
import com.micro.fast.log.service.SysLogService;
import com.micro.fast.utils.address.AddressUtils;
import com.micro.fast.utils.code.ResultCode;
import com.micro.fast.utils.ip.IpUtils;
import com.micro.fast.utils.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;


/**
 * 日志切面
 *
 * @author shoufeng
 */
@Aspect
@Slf4j
public class SysLogAspect {

	@Resource
	private ApplicationContext applicationContext;
	@Resource
	private HttpServletRequest httpServletRequest;
	@Resource
	private SysLogService sysLogService;


	@Pointcut("@annotation(com.micro.fast.log.annotation.SysLog)")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long startTime = System.currentTimeMillis();
		//执行方法
		Object result = point.proceed();
		//执行时长(毫秒)
		long totalMillisecond = System.currentTimeMillis() - startTime;
		SysLogDto sysLogDto = new SysLogDto();
		sysLogDto.setExecuteTime(totalMillisecond);

		//设置sessionId
		sysLogDto.setSessionId(httpServletRequest.getRequestedSessionId());

		MethodSignature signature = (MethodSignature) point.getSignature();
		Method method = signature.getMethod();
		SysLog syslog = method.getAnnotation(SysLog.class);
		if (syslog != null) {
			sysLogDto.setOperation(syslog.operation());
		}

		//请求的方法名
		String className = point.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLogDto.setMethod(className + "." + methodName + "()");

		//请求的参数
		Object[] args = point.getArgs();
		if (ArrayUtils.isNotEmpty(args)) {
			String reqParam = JSON.toJSONString(args[0]);
			sysLogDto.setReqParam(reqParam);
		}

		//设置IP地址
		sysLogDto.setIp(IpUtils.getIpAddr(httpServletRequest));

		//用户名
		Result<SysUserInfoDto> sysUserInfoBaseDtoResult = sysLogService.getUserInfo();
		if (sysUserInfoBaseDtoResult.getCode().equals(ResultCode.SUCCESS.getCode()) && ObjectUtils.isNotEmpty(sysUserInfoBaseDtoResult.getData())) {
			sysLogDto.setUsername(sysUserInfoBaseDtoResult.getData().getUsername());
		}
		String region = AddressUtils.getRegionByIp(sysLogDto.getIp());
		sysLogDto.setRegion(region);
		Date now = new Date();
		sysLogDto.setCreateTime(now);
		sysLogDto.setUpdateTime(now);

		if (syslog.enableDb()) {
			//日志异步入库
			applicationContext.publishEvent(new SysLogEvent(sysLogDto));
		}

		log.info("请求: {}", sysLogDto);

		return result;
	}

}
