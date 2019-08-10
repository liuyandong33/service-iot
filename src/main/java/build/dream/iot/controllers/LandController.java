package build.dream.iot.controllers;

import build.dream.common.annotations.PermitAll;
import build.dream.common.utils.ConfigurationUtils;
import build.dream.iot.constants.Constants;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.bean.ProducerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

@PermitAll
@Controller
@RequestMapping(value = "/land")
public class LandController {
    //    @Autowired
//    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private ProducerBean producerBean;

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test() {
//        String topic = ConfigurationUtils.getConfiguration("delayed.or.timed.rocket.mq.topic");
//        Message<String> message = new GenericMessage<String>(UUID.randomUUID().toString());
//        SendResult sendResult = rocketMQTemplate.syncSend(topic, message, 30000, 30000);
//        return Constants.SUCCESS;

        Message message = new Message();
        message.setTopic(ConfigurationUtils.getConfiguration("delayed.or.timed.rocket.mq.topic"));
        message.setBody(UUID.randomUUID().toString().getBytes(Constants.CHARSET_UTF_8));
        message.setStartDeliverTime(System.currentTimeMillis() + 30000);
        SendResult sendResult = producerBean.send(message);
        return Constants.SUCCESS;
    }
}
