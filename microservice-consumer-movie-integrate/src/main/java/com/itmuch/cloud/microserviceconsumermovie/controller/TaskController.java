package com.itmuch.cloud.microserviceconsumermovie.controller;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itmuch.cloud.microserviceconsumermovie.pojo.ResponseMsg;

/**
 * 测试异步返回结果-- 失败了,需要重新再试
 * 
 * @author Administrator
 *
 */
@RestController
public class TaskController {

	private static final Logger log = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	private TaskService taskService;

	@GetMapping("/getMsg")
	public Callable<ResponseMsg<String>> getResult(){
		
		log.info("接收请求，开始处理...");

        Callable<ResponseMsg<String>> result = (()->{
            return taskService.getResult();
        });

        log.info("接收任务线程完成并退出");

        return result;
	}


}
