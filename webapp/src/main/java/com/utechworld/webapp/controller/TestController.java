package com.utechworld.webapp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gehaisong
 */
@RefreshScope
@RestController
@RequestMapping("/web")
public class TestController {
//    @Value("${name}")
    String userName;

    @RequestMapping(value = "index")
    public String index (HttpServletRequest request){

        return  "--d--"+userName;
    }

}
