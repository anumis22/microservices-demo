package com.example.restfulwebservices;

import com.example.restfulwebservices.model.HelloWorldBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;
    //GET
    //URI - /hello-world
    //method - "Hello World"
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!";
    }

    //hello-world - return a bean
    @GetMapping("/hello-bean")
    public HelloWorldBean helloWorldBean() {
        return new HelloWorldBean("Hello World!");
    }

    //hello-world/path-variable/anu
    @GetMapping("/hello/{name}")
    public HelloWorldBean helloWorldPathVariable(@PathVariable  String name) {
        return new HelloWorldBean(String.format("Hello, %s",name));
    }

    //Internationalization - i18n
    @GetMapping("/hello-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required =false) Locale locale) {
        return messageSource.getMessage("good.morning.message",null,locale);
    }
}
