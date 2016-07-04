package com.yiwugou.reb.client.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yiwugou.reb.api.entity.user.User;
import com.yiwugou.reb.client.user.CacheUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:conf/spring/reb-client.xml", "classpath*:conf/spring/reb-client-dubbo-consumer.xml" })
// @ActiveProfiles("test")
public class DemoTest {

    @Resource
    private CacheUserService cacheUserService;

    @Test
    public void test() {
        User user = this.cacheUserService.cacheSelect(53L);
        System.err.println(user);
    }
}
