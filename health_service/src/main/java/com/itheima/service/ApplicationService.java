package com.itheima.service;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class ApplicationService {
    public static void main(String[] args) throws IOException {
        new ClassPathXmlApplicationContext("classpath:applicationContext-service.xml");
        System.in.read();
    }
}
