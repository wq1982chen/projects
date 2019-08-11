package com.itmuch.cloud.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/*
 * 在windows命令行中执行以下语句：
 * keytool -genkeypair -alias mytestkey -keyalg RSA -dname "CN=Web Server,OU=Unit,O=Organization,L=City,S=State,C=US" -keypass changeme -keystore server.jks -storepass letmein
-alias:别名
-keyalg: 指定密钥使用的加密算法（如 RSA ）
-validity:过期时间，单位天
-keystore: 指定存储密钥的密钥库的名称（二进制文件）
-keypass: 指定生成密钥的密码。 
-storepass: 指定访问密钥库的密码
生成证书后可使用：keytool -list -v -keystore d:/mytestkey.keystore 查看证书详细情况。
 * 生成的server.jks放到resources下,记得刷新一下.
 * 增加boostrap.yml文件及里面的配置
 * 服务启动，在docker中执行以下语句
 *  curl http://192.168.0.7:8080/encrypt -d mysecret
 */
@SpringBootApplication
@EnableConfigServer
public class MicroserviceConfigServerEncryptionRSAApplication {
    public static void main( String[] args ){
    	SpringApplication.run(MicroserviceConfigServerEncryptionRSAApplication.class, args);
    }
  
}
