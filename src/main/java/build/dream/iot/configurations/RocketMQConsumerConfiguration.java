package build.dream.iot.configurations;

import build.dream.common.annotations.RocketMQMessageListener;
import build.dream.common.tuples.Tuple2;
import build.dream.common.utils.TupleUtils;
import build.dream.iot.listeners.DelayedOrTimedRocketMQListener;
import build.dream.iot.rocketmq.RocketMQProperties;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Properties;

@Configuration
public class RocketMQConsumerConfiguration {
    @Autowired
    private RocketMQProperties rocketMQProperties;
    @Autowired
    private DelayedOrTimedRocketMQListener delayedOrTimedRocketMQListener;

    @Bean
    public Consumer consumer() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, rocketMQProperties.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, rocketMQProperties.getSecretKey());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, rocketMQProperties.getNameSrvAddr());
        properties.put(PropertyKeyConst.GROUP_ID, rocketMQProperties.getGroupId());
        Consumer consumer = ONSFactory.createConsumer(properties);

        // 开始订阅消息
        subscribe(consumer, delayedOrTimedRocketMQListener);

        consumer.start();
        return consumer;
    }

    private void subscribe(Consumer consumer, MessageListener messageListener) {
        Tuple2<String, String> tuple2 = obtainTopicAndSubExpression(messageListener.getClass());
        consumer.subscribe(tuple2._1(), tuple2._2(), messageListener);
    }

    private Tuple2<String, String> obtainTopicAndSubExpression(Class<? extends MessageListener> clazz) {
        RocketMQMessageListener rocketMQMessageListener = AnnotationUtils.findAnnotation(clazz, RocketMQMessageListener.class);
        return TupleUtils.buildTuple2(rocketMQMessageListener.topic(), rocketMQMessageListener.subExpression());
    }
}
