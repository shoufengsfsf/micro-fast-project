package com.micro.fast.demo.mq;


import com.micro.fast.demo.mq.service.producer.DemoProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author shoufeng
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTest {

    @Resource
    private DemoProducer demoProducer;

    @Test
    public void test() {
        demoProducer.sendMessage();
    }
}
