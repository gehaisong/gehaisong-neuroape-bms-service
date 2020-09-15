package com.utechworld.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by gehaisong
 */
@RefreshScope
@RestController
@RequestMapping("/web")
public class TestController {

    @Autowired
    TestFeignService testFeignService;
    @Value("${name}")
    String userName;

    @RequestMapping(value = "index")
    public List index (HttpServletRequest request){
        List list = testFeignService.getUserList(0,10);
        return list ;
    }

}
