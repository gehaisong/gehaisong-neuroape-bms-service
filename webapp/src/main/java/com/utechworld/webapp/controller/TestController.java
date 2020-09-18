package com.utechworld.webapp.controller;

import com.utechworld.neuroape.common.result.ResultPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "index/{pageNo}/{pageSize}/{search}")
    public ResultPage index (@PathVariable Integer pageNo, @PathVariable Integer pageSize,@PathVariable String search){
        ResultPage result = testFeignService.getUserList(pageNo,pageSize,search);
        return result ;
    }

}
