package com.itmuch.cloud.microserviceprovideruser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 使用spring boot actuator来对web服务进行监控
 * spring boot项目集成actuator非常容易，
 * 只需要添加一个actuator的maven依赖就好了
 * http://localhost:8000/actuator
 * http://localhost:8000/actuator/health 
 * http://localhost:8000/actuator/info
 * actuator默认只暴露了几个简单的endpoint
 * 开启所有接口：
 * management.endpoints.web.exposure.include="*"
 * http://localhost:8000/actuator
 * http://localhost:8000/actuator/metrics
 * http://localhost:8000/actuator/metrics/http.server.requests
 * http://localhost:8000/actuator/metrics/http.server.requests?tag=uri:/actuator/metrics	
 */
/**
 * 增加sleuth依赖
 * 增加DispatchServlet日志级别
 * 
 * @author Administrator
 *
 */
/**
一、使用docker启动zipkin容器,此种方式链路信息仅存放于内存中
docker run -it -d -p 9411:9411 --name zipkin-server openzipkin/zipkin
* 使用http://192.168.99.100:9411访问zipkin

二、使用mysql储存链路跟踪信息,zipkin启动方式，mysql在本地,zipkin在docker,需要使用--net host方式
docker run -d \
--restart always \
-v /etc/localtime:/etc/localtime:ro \
-e MYSQL_USER=root \
-e MYSQL_PASS=root \
-e MYSQL_HOST=172.16.20.126 \
-e STORAGE_TYPE=mysql \
-e MYSQL_DB=test2 \
-e MYSQL_TCP_PORT=3306 \
--net host \
--name zipkin2mysql \
openzipkin/zipkin
链路信息存放到mysql后，并不能马上在页面上看到，需要等待1分钟左右

三、通过rabbitmq来收集链路跟踪信息,仍然是通过zipkin来展示,链路数据未保存
1. 下载安装rabbitmq
docker search rabbitmq:management
2、docker pull rabbitmq:management
3、开始创建rabbitmq容器
docker run -d \
--name rabbitmq \
--publish 5671:5671 \
--publish 5672:5672 \
--publish 4369:4369 \
--publish 25672:25672 \
--publish 15671:15671 \
--publish 15672:15672 \
rabbitmq:management
4、容器启动之后就可以访问web 管理端
http://http://192.168.99.100:15672，默认用户 guest ，密码 guest。

docker run -it -d -p 9411:9411  
-e zipkin.collector.rabbitmq.addresses=172.17.0.2:5672 \
-e zipkin.collector.rabbitmq.username=guest \
-e zipkin.collector.rabbitmq.password=guest \
-e zipkin.collector.rabbitmq.queue=zipkin
-e RABBIT_ADDRESSES=SpringApplication \
--name zipkin_rabbit openzipkin/zipkin

RABBIT_ADDRESSES这个参数相当重要,只有配置了才能让zipkin从mq中拉取消息
其他说明:
zipkin.collector.rabbitmq.concurrency	 并发消费者数量，默认为1
zipkin.collector.rabbitmq.connection-timeout	 建立连接时的超时时间，默认为 60000毫秒，即 1 分钟
使用参数 zipkin.collector.rabbitmq.uri=amqp://guest:guest@host:10000/vhost
以下的参数将被忽略:
zipkin.collector.rabbitmq.addresses
zipkin.collector.rabbitmq.password	
zipkin.collector.rabbitmq.username
zipkin.collector.rabbitmq.virtual-host	使用的 RabbitMQ virtual host，默认为 /
zipkin.collector.rabbitmq.use-ssl	设置为true则用 SSL 的方式与 RabbitMQ 建立链接

参考范例:https://windmt.com/2018/04/24/spring-cloud-12-sleuth-zipkin/
*/
@SpringBootApplication
public class MicroserviceProviderUserApplication 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(MicroserviceProviderUserApplication.class, args);
    }
}
