package com.itmuch.cloud.microservice.zipkinserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin2.server.internal.EnableZipkinServer;

/**
 * Spring Boot 2.0之后，使用EnableZipkinServer创建自定义的zipkin服务器已经被废弃，将无法启动
 * 所以本项目在启动过程中的问题就不在解决了
 */
@SpringBootApplication
@EnableZipkinServer
public class ZipkinServerApplication {
    public static void main( String[] args ){
        SpringApplication.run(ZipkinServerApplication.class, args);
    }
}
