package build.dream.iot.controllers;

import build.dream.common.annotations.PermitAll;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@PermitAll
@Controller
@RequestMapping(value = "/land")
public class LandController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping(value = "/test")
    public String test() {
        rocketMQTemplate.syncSend("_test_rocket_mq", "Hello, World!");
        return "ping/upload";
    }
}
