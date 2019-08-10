package com.test.springboot.webflux;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import junit.framework.TestCase;

@RunWith(SpringRunner.class)
@WebFluxTest(controllers = HelloController.class)
public class TestHelloController  extends TestCase {
	
	@Autowired
    WebTestClient client;
	
	@Test
    public void getHelloCtl() {
        client.get().uri("/helloCtl").exchange().expectStatus().isOk();
    }
}
