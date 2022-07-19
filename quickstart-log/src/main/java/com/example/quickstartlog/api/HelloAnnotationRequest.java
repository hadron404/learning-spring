package com.example.quickstartlog.api;

import com.example.quickstartlog.annotation.LogMethod;
import com.example.quickstartlog.annotation.ParameterType;
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
public class HelloAnnotationRequest {


    @LogMethod(value = {ParameterType.IN, ParameterType.OUT})
    @GetMapping("/annotation")
    public String getHello(HelloForm helloForm) {
        return "Hello annotation";
    }

    @LogMethod(value = {ParameterType.IN})
    @GetMapping("/annotation/in")
    public String getHelloIn(HelloForm helloForm) {
        return "Hello annotation";
    }

    @LogMethod(value = {ParameterType.OUT})
    @GetMapping("/annotation/out")
    public String getHelloOut(HelloForm helloForm) {
        return "Hello annotation";
    }

    @LogMethod()
    @GetMapping("/annotation/default")
    public String getHelloDefault(HelloForm helloForm) {
        return "Hello annotation";
    }

}
