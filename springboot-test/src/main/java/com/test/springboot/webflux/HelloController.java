package com.test.springboot.webflux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
public class HelloController {

	@GetMapping("/helloCtl")
    public Mono<String> helloCtl() {
        return Mono.just("Welcome to reactive world ~");
    }
}
