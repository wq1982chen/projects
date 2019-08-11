package com.itmuch.cloud.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/*
 * 有跟描述不一致的地方:
 *  encrypt.key属性一定要设置到bootstrap.yml中,否则不会生效.
 *  
 *  如何得到加密值:
 *    启动服务,使用config server的/encrypt端点加密,/decrypt端点解密
 *    我是在docker里面，使用curl http://192.168.0.7:8080/encrypt -d KevinPwd
 *    即得到KevinPwd的加密字符串
 *  测试方式:
 *  新建一个encryption.yml的文件，里面设置一个属性,属性值使用加密值。
 *  上传到github的repository,这个与application.yml里面配置的仓库地址一致
 *  然后，访问这个地址http://localhost:8080/encryption-default.yml 可以看到已被解密的加密信息
 *  如果想要看到加密密文,见application.yml中的描述
 */
@SpringBootApplication
@EnableConfigServer
public class MicroserviceConfigServerlEncryptApplication {
    public static void main( String[] args ){
    	SpringApplication.run(MicroserviceConfigServerlEncryptApplication.class, args);
    }
  
}
