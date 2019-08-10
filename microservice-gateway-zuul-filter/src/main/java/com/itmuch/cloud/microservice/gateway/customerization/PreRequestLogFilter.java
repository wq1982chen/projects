package com.itmuch.cloud.microservice.gateway.customerization;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class PreRequestLogFilter extends ZuulFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(PreRequestLogFilter.class);
	
	/**
	 * 本过滤器执行的开关
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	 /**
	  * 过滤器执行的逻辑写在这个方法里
	  */
	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request =  ctx.getRequest();
		PreRequestLogFilter.LOGGER.info(String.format("send %s request to %s",request.getMethod(),
				request.getRequestURI().toString()));
		return null;
	}

	/**
	 * 取值包括:
	 * pre : 过滤器在请求被路由之前调用
	 * route:将请求路由到微服务
	 * post: 将请求路由到微服务后执行
	 * error: 其他阶段执行错误时执行该过滤器
	 * 详细可参详com.netflix.zuul.ZuulFilter.filterType()中的注释
	 */
	@Override
	public String filterType() {
		return "pre";
	}

	/**
	 * 指定过滤器执行的顺序号
	 * 不同的过滤器允许返回相同的数字
	 */
	@Override
	public int filterOrder() {
		return 1;
	}

}
