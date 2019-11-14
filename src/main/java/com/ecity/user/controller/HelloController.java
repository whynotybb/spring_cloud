package com.ecity.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {
    private final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/hello")
    public String hello(String userName) {
        List<String> services = discoveryClient.getServices();
        for (String serviceId : services) {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceId);
            for (ServiceInstance instance : serviceInstances)
                log.info("/hello,host" + instance.getHost() + ",serviceId" + instance.getServiceId());
        }
        return "Hello "+userName;
    }
}
