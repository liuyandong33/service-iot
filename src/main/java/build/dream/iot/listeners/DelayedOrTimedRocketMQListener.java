package build.dream.iot.listeners;

import build.dream.common.annotations.RocketMQMessageListener;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(topic = "${delayed.or.timed.rocket.mq.topic}")
public class DelayedOrTimedRocketMQListener implements MessageListener {
    @Override
    public Action consume(Message message, ConsumeContext context) {
        System.out.println(message);
        return Action.CommitMessage;
    }
}
