package build.dream.iot.configurations;

import build.dream.common.annotations.RocketMQMessageListener;
import build.dream.iot.rocketmq.RocketMQProperties;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class RocketMQConsumerConfiguration {
    @Autowired
    private RocketMQProperties rocketMQProperties;
    private static List<MessageListener> messageListeners = new ArrayList<MessageListener>();

    public static void addMessageListener(MessageListener messageListener) {
        messageListeners.add(messageListener);
    }

    @Bean
    public Consumer consumer() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, rocketMQProperties.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, rocketMQProperties.getSecretKey());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, rocketMQProperties.getNameSrvAddr());
        properties.put(PropertyKeyConst.GROUP_ID, rocketMQProperties.getGroupId());
        Consumer consumer = ONSFactory.createConsumer(properties);

        for (MessageListener messageListener : messageListeners) {
            subscribe(consumer, messageListener);
        }

        consumer.start();
        return consumer;
    }

    private void subscribe(Consumer consumer, MessageListener messageListener) {
        RocketMQMessageListener rocketMQMessageListener = AnnotationUtils.findAnnotation(messageListener.getClass(), RocketMQMessageListener.class);
        consumer.subscribe(rocketMQMessageListener.topic(), rocketMQMessageListener.subExpression(), messageListener);
    }
}
