package com.utechworld.bms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.TimeUnit;

@MapperScan("com.utechworld.**.mapper")
@SpringBootApplication
@RefreshScope
@EnableDiscoveryClient //开启服务注册发现功能
public class BmsServiceApplication {

	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext applicationContext =  SpringApplication.run(BmsServiceApplication.class, args);
			//当动态配置刷新时，会更新到 Enviroment中，因此这里每隔一秒中从Enviroment中获取配置
//			String userName = applicationContext.getEnvironment().getProperty("nacos.config.age");
//			String app = applicationContext.getEnvironment().getProperty("spring.application.name");
//			System.err.println("user name :" + userName+";app:" + app );
//			TimeUnit.SECONDS.sleep(3);
	}

}
