/*package com.jdd.immanage.dao.test;

import com.jdd.imadmin.common.redis.RedisManager;
import com.jdd.imadmin.manage.ManageApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ManageApplication.class)
@RunWith(SpringRunner.class)
public class InitRedisCusterTest {
    @Autowired
    RedisManager redisManager;

    @Test
    public void initTest(){
        redisManager.set("im-admin","1111111");
        System.out.println(redisManager.get("im-admin"));
    }
}*/
