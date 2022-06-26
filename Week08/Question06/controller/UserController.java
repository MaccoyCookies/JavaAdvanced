package com.maccoy.sharding.project.controller;

import com.maccoy.sharding.project.domain.User;
import com.maccoy.sharding.project.mapper.UserMapper;
import com.maccoy.sharding.project.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private IUserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/add")
    public String add(@RequestBody User user) {
        userMapper.insertSelective(user);
        return "success";
    }

    @PostMapping("/info/{id}")
    public User info(@PathVariable("id") Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @GetMapping("/list")
    public List<User> list() {
        return userMapper.list();
    }

    @GetMapping("/commit")
    public String commit() {
        userService.commit();
        return "success";
    }

    @GetMapping("/rollback")
    public String rollback() {
        userService.rollback();
        return "success";
    }

}
