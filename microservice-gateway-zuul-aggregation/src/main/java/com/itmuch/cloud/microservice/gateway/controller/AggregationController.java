package com.itmuch.cloud.microservice.gateway.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.collect.Maps;
import com.itmuch.cloud.microservice.gateway.customerization.AggregationService;
import com.itmuch.cloud.microservice.gateway.model.User;

import rx.Observable;
import rx.Observer;

@RestController
public class AggregationController {

	public static final Logger LOGGER = LoggerFactory.getLogger(AggregationController.class);
	
	@Autowired
	private AggregationService aggregationService;
	
	//http://localhost:8040/aggregate/1
	@GetMapping("/aggregate/{id}")
	public DeferredResult<HashMap<String, User>> aggregation(@PathVariable Long id) {
		Observable<HashMap<String,User>> result = this.aggregateObservable(id);
		return this.toDeferredResult(result);
	}
	
	public Observable<HashMap<String,User>> aggregateObservable(Long id) {
		//合并两个或者多个Observable发射出的数据项,根据指定的函数变换他们
		return Observable.zip(
				this.aggregationService.getUserById(id),
				this.aggregationService.getMoiveUserById(id),
//				new Func2<User, User, HashMap<String,User>>() {
//					@Override
//					public HashMap<String, User> call(User user, User moiveUser) {
//						HashMap<String,User> map = Maps.newHashMap();
//						map.put("user",user);
//						map.put("moiveUser",moiveUser);
//						return map;
//					}
//				}
				(user,moiveUser) -> {
					HashMap<String,User> map = Maps.newHashMap(); //google guava的一种写法,可以简化不用写泛型
					map.put("user",user);
					map.put("moiveUser",moiveUser);
					return map;
				});
	}
	
	private DeferredResult<HashMap<String, User>> 
		toDeferredResult(Observable<HashMap<String, User>> detail) {
		
		DeferredResult<HashMap<String, User>> result = new  DeferredResult<>();
		detail.subscribe( new Observer<HashMap<String, User>>() {
			@Override
			public void onCompleted() {
				LOGGER.info("完成...");
			}
			
			@Override
			public void onError(Throwable e) {
				LOGGER.error("发生错误.",e);
			}
			
			@Override
			public void onNext(HashMap<String, User> moiveDetail) {
				result.setResult(moiveDetail);
			}
		});
		return result;
	}

	
}
