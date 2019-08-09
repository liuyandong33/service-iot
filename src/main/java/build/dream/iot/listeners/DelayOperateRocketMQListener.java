package build.dream.iot.listeners;

import build.dream.common.utils.JacksonUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RocketMQMessageListener(topic = "delay_operate_rocket_mq_topic", consumerGroup = "GID_delay_operate")
public class DelayOperateRocketMQListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        Map<String, Object> messageMap = JacksonUtils.readValueAsMap(message, String.class, Object.class);
        int type = MapUtils.getIntValue(messageMap, "type");

        switch (type) {
            case 1:
                break;
        }
    }
}
