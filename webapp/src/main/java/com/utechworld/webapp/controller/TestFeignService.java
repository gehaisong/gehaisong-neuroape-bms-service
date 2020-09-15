package com.utechworld.webapp.controller;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by gehaisong
 */
@FeignClient(name = "neuroape-bms-service",url = "http://127.0.0.1:8800/boot")
public interface TestFeignService {

    @GetMapping("users/{pageNo}/{pageSize}")
    public List getUserList (@PathVariable Integer pageNo, @PathVariable Integer pageSize);

    @GetMapping(value = "/redis")
    public List<Integer> redisTest();
}
