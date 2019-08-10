package build.dream.iot.configurations;

import build.dream.iot.rocketmq.RocketMQProperties;
import com.aliyun.openservices.ons.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class RocketMQConsumerConfiguration {
    @Autowired
    private RocketMQProperties rocketMQProperties;

    @Bean
    public Consumer consumer() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, rocketMQProperties.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, rocketMQProperties.getSecretKey());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, rocketMQProperties.getNameSrvAddr());
        properties.put(PropertyKeyConst.GROUP_ID, rocketMQProperties.getGroupId());
        Consumer consumer = ONSFactory.createConsumer(properties);

        String topic = "development_ze1_iot_delayed_or_timed_rocket_mq_topic";
        consumer.subscribe(topic, "*", new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
                System.out.println("Receive: " + message);
                return Action.CommitMessage;
            }
        });
        consumer.start();
        return consumer;
    }
}
