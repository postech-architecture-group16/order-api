package com.fiap.challenge.order.infra.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.challenge.order.infra.mq.MqProducer;

@RestController
@RequestMapping("/teste")
public class TesteController {

    @Autowired
    private MqProducer mqProducer;

    @GetMapping
    public String send(){
    	mqProducer.send("test message");
        return "ok. done";
    }

}