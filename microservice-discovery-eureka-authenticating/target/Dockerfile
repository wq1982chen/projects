FROM kk/jdk1.8.0_201
MAINTAINER wangqing920928@126.com
VOLUME /tmp
ADD microservice-descovery-eureka-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'
EXPOSE 8761
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]