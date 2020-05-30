package com.micro.fast.rabbit.configuration;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.micro.fast.rabbit.configuration.properties.MicroFastRabbitMqProperties;
import com.micro.fast.rabbit.constant.ExchangeTypeEnum;
import com.micro.fast.rabbit.constant.MicroFastRabbitExceptionCodeEnum;
import com.micro.fast.rabbit.constant.QueueArgumentEnum;
import com.micro.fast.rabbit.exception.MicroFastRabbitException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * rabbitmq配置
 *
 * @author shoufeng
 */

@Configuration
@ConditionalOnProperty(prefix = "micro.fast.rabbit", name = "enable", havingValue = "true")
@ComponentScan(value = "com.micro.fast.rabbit.configuration")
@Slf4j
public class MicroFastRabbitMqConfiguration {

    @Resource
    private MicroFastRabbitMqProperties microFastRabbitMqProperties;

    @Resource
    private BeanFactory beanFactory;

    @Bean
    public List<Binding> bindingList() {
        if (CollectionUtils.isEmpty(microFastRabbitMqProperties.getBindingList())) {
            return Lists.newArrayList();
        }
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<MicroFastRabbitMqProperties.Binding> microFastRabbitMqPropertiesBindingList = microFastRabbitMqProperties.getBindingList();
        return microFastRabbitMqPropertiesBindingList
                .stream()
                .map(binding -> {
                    MicroFastRabbitMqProperties.Exchange bindingExchange = binding.getExchange();
                    MicroFastRabbitMqProperties.Queue bindingQueue = binding.getQueue();
                    ExchangeTypeEnum exchangeTypeEnum = bindingExchange.getExchangeTypeEnum();
                    Binding realBinding;
                    Queue queue = new Queue(bindingQueue.getName(), bindingQueue.isDurable(), bindingQueue.isExclusive(), bindingQueue.isAutoDelete(), bindingQueue.getArguments());
                    configurableBeanFactory.registerSingleton(bindingQueue.getName(), queue);
                    bindingQueue.getArguments().forEach((argumentKey, argumentValue) -> {
                        JSONObject argLogJsonObject = new JSONObject();
                        argLogJsonObject.put("键", "argumentKey");
                        argLogJsonObject.put("值", argumentValue);
                        Optional.ofNullable(QueueArgumentEnum.KEY_QUEUE_ARGUMENT_ENUM_MAP.get(argumentKey)).ifPresent(queueArgumentEnum -> {
                            argLogJsonObject.put("描述", queueArgumentEnum.getDescription());
                        });
                        log.info("队列[{}]设置参数: {}", bindingQueue.getName(), argLogJsonObject.toJSONString());
                    });
                    switch (exchangeTypeEnum) {
                        case DIRECT_EXCHANGE: {
                            DirectExchange directExchange;
                            try {
                                directExchange = configurableBeanFactory.getBean(bindingExchange.getName(), DirectExchange.class);
                            } catch (Exception e) {
                                directExchange = ObjectUtils.isEmpty(bindingExchange.getArguments())
                                        ? new DirectExchange(bindingExchange.getName(), bindingExchange.isDurable(), bindingExchange.isAutoDelete())
                                        : new DirectExchange(bindingExchange.getName(), bindingExchange.isDurable(), bindingExchange.isAutoDelete(), bindingExchange.getArguments());
                                configurableBeanFactory.registerSingleton(bindingExchange.getName(), directExchange);
                            }

                            realBinding = BindingBuilder.bind(queue).to(directExchange).with(binding.getRoutingKey());
                            break;
                        }
                        case FANOUT_EXCHANGE: {
                            FanoutExchange fanoutExchange;
                            try {
                                fanoutExchange = configurableBeanFactory.getBean(bindingExchange.getName(), FanoutExchange.class);
                            } catch (Exception e) {
                                fanoutExchange = ObjectUtils.isEmpty(bindingExchange.getArguments())
                                        ? new FanoutExchange(bindingExchange.getName(), bindingExchange.isDurable(), bindingExchange.isAutoDelete())
                                        : new FanoutExchange(bindingExchange.getName(), bindingExchange.isDurable(), bindingExchange.isAutoDelete(), bindingExchange.getArguments());
                                configurableBeanFactory.registerSingleton(bindingExchange.getName(), fanoutExchange);
                            }

                            realBinding = BindingBuilder.bind(queue).to(fanoutExchange);
                            break;
                        }
                        case TOPIC_EXCHANGE: {
                            TopicExchange topicExchange;
                            try {
                                topicExchange = configurableBeanFactory.getBean(bindingExchange.getName(), TopicExchange.class);
                            } catch (Exception e) {
                                topicExchange = ObjectUtils.isEmpty(bindingExchange.getArguments())
                                        ? new TopicExchange(bindingExchange.getName(), bindingExchange.isDurable(), bindingExchange.isAutoDelete())
                                        : new TopicExchange(bindingExchange.getName(), bindingExchange.isDurable(), bindingExchange.isAutoDelete(), bindingExchange.getArguments());
                                configurableBeanFactory.registerSingleton(bindingExchange.getName(), topicExchange);
                            }

                            realBinding = BindingBuilder.bind(queue).to(topicExchange).with(binding.getRoutingKey());
                            break;
                        }
                        default: {
                            throw new MicroFastRabbitException(MicroFastRabbitExceptionCodeEnum.FAIL.getCode(), "无效交换机类型: {}", exchangeTypeEnum);
                        }
                    }
                    configurableBeanFactory.registerSingleton(binding.getName(), realBinding);
                    return realBinding;
                }).collect(Collectors.toList());
    }

}
