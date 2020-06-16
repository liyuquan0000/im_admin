package com.jdd.imadmin.dao.test;

import com.jdd.imadmin.dao.mapper.TestMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class InitTest {
    @Autowired
    private TestMapper testMapper;

    @Test
    public void testDaoInit() {
        testMapper.selectTest();
        Assert.assertTrue(testMapper.selectTest().size() == 1);
    }
}
