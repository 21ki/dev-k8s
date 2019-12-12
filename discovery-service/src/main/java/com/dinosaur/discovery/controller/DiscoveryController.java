package com.dinosaur.discovery.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
public class DiscoveryController {

	@Autowired
    private DiscoveryClient discoveryClient;

    /**
     * 探针检查响应类
     * @return
     */
    @RequestMapping("/health")
    public String health() {
        return "health";
    }

    /**
     * 返回远程调用的结果
     * @return
     * @throws JsonProcessingException 
     */
    @RequestMapping("/getservicedetail")
    public String getservicedetail (
            @RequestParam(value = "servicename", defaultValue = "") String servicename) throws JsonProcessingException {
    	ObjectMapper mapper = new ObjectMapper();
    	
        return "Service [" + servicename + "]'s instance list : " + mapper.writeValueAsString(discoveryClient.getInstances(servicename));
    }

    /**
     * 返回发现的所有服务
     * @return
     */
    @RequestMapping("/services")
    public String services() {
        return this.discoveryClient.getServices().toString()
                + ", "
                + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
