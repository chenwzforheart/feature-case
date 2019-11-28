package com.example.demo;

import com.example.demo.dto.*;
import com.example.demo.dto.User;
import com.example.demo.service.*;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * Created by wenzheng.chen on 2018/1/29.
 */

@FeignClient(value = "hello-service")
public interface RefactorHelloService extends com.example.demo.service.HelloService {

}
