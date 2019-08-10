package build.dream.iot.controllers;

import build.dream.common.annotations.PermitAll;
import build.dream.common.utils.ConfigurationUtils;
import build.dream.iot.constants.Constants;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@PermitAll
@Controller
@RequestMapping(value = "/land")
public class LandController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test() {
        String topic = ConfigurationUtils.getConfiguration("delayed.or.timed.rocket.mq.topic");
        Message<String> message = new GenericMessage<String>(UUID.randomUUID().toString());
        SendResult sendResult = rocketMQTemplate.syncSend(topic, message, 30000, 30000);
        return Constants.SUCCESS;
    }
}
