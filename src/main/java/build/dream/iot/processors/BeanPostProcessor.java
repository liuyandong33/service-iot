package build.dream.iot.processors;

import build.dream.iot.configurations.RocketMQConsumerConfiguration;
import com.aliyun.openservices.ons.api.MessageListener;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

@Component
public class BeanPostProcessor implements org.springframework.beans.factory.config.BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof MessageListener) {
            MessageListener messageListener = (MessageListener) bean;
            RocketMQConsumerConfiguration.addMessageListener(messageListener);
        }
        return bean;
    }
}
