package com.example.quickstartlog.api.controller;

import com.example.quickstartlog.model.HelloForm;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @author zhouqiang
 */
@RestController
@RequestMapping("/hello")
@SuppressWarnings("unused")
public class HelloController {

    @GetMapping()
    public String getHello(HelloForm helloForm) {
        return "Hello";
    }
}
