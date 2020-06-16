package com.jdd.imadmin.dao.mapper;


import com.jdd.imadmin.dao.entity.Test;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestMapper {
    List<Test> selectTest();
}
