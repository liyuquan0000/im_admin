package com.jdd.imadmin.api.test;

import com.jdd.imadmin.api.ApiApplication;
import com.jdd.imadmin.dao.mapper.TestMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = ApiApplication.class)
@RunWith(SpringRunner.class)
public class TestDao {
    @Autowired
    private TestMapper testMapper;

    @Test
    public void testDaoInit() {
        testMapper.selectTest();
        Assert.assertTrue(testMapper.selectTest().size() == 1);
    }
}
