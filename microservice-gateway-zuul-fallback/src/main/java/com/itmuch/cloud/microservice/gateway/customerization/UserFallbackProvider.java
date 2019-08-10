package com.itmuch.cloud.microservice.gateway.customerization;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

/**
 *  跟书上的有点不一致,要使用FallbackProvider替代ZuulFallbackProvider
 *  https://blog.csdn.net/H_O_W_E/article/details/87929508
 * @author Administrator
 */
@Component
public class UserFallbackProvider implements FallbackProvider {

	@Override
	public String getRoute() {
		/*
		 * 表名是哪个微服务提供回退
		 */
		return "microservice-provider-user";
	}

	@Override
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		return new ClientHttpResponse() {
			
			@Override
			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				MediaType mt = new MediaType( MediaType.APPLICATION_JSON, 
						Charset.forName("UTF-8"));
				headers.setContentType(mt );
				return headers;
			}
			
			@Override
			public InputStream getBody() throws IOException {
				return new ByteArrayInputStream("用户微服务不可用,请稍后再试".getBytes());
			}
			
			@Override
			public String getStatusText() throws IOException {
				return this.getStatusCode().getReasonPhrase();
			}
			
			@Override
			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.OK;
			}
			
			@Override
			public int getRawStatusCode() throws IOException {
				return 200;
			}
			
			@Override
			public void close() {}
		};
	}

}
