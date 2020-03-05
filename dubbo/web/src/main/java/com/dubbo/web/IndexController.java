package com.dubbo.web;

import com.dubbo.provider.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author csh9016
 * @date 2020/3/5
 */
@RestController
public class IndexController {

    @Resource(name = "orderService")
    private OrderService orderService;

    @RequestMapping("/hello")
    public void test() {
        orderService.print();
    }
}
