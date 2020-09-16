package com.utechworld.webapp.controller;


import com.utechworld.neuroape.common.result.Result;
import com.utechworld.neuroape.common.result.ResultPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by gehaisong
 */
@FeignClient(name = "neuroape-bms-service")
public interface TestFeignService {

    @GetMapping("/boot/users/{pageNo}/{pageSize}/{search}")
    public ResultPage getUserList (@PathVariable Integer pageNo, @PathVariable Integer pageSize,@PathVariable String search);

    @GetMapping(value = "/redis")
    public Result redisTest();
}
