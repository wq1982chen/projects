package com.itmuch.cloud.microservice.uploadfile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroserviceUploadFileApplication {
    public static void main( String[] args ){
    	SpringApplication.run(MicroserviceUploadFileApplication.class, args);
    }
  /*
   * 通过zuul实现upload file ,不过遇到一个问题,内嵌的tomat对文件上传有大小限制，默认是1MB
   * 所以要在zuul和本工程中都设置tomcat取消这个限制
   * http://localhost:8040/microservice-file-upload/upload
   */
}
