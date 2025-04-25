package com.aixbox.system.controller;


import com.aixbox.system.domain.entity.Test;
import com.aixbox.system.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestMapper testMapper;

    @PostMapping("/save")
    public String save(Test test) {
        testMapper.insert(test);
        return "success";
    }

    @GetMapping("/get")
    public Test getById(Integer id) {
        return testMapper.selectByPrimaryKey(id);
    }


}
