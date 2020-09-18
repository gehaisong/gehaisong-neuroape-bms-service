package com.utechworld.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@EnableFeignClients
@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient //开启服务注册发现功能
public class WebappApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext =  SpringApplication.run(WebappApplication.class, args);
		String userName = applicationContext.getEnvironment().getProperty("spring.cloud.nacos.discovery.server-addr");
		System.out.println("name="+userName);
	}

}
