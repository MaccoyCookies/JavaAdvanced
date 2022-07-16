package io.kimmking.cache.service;

import cn.hutool.core.util.IdUtil;
import io.kimmking.cache.entity.User;
import io.kimmking.cache.mapper.UserMapper;
import io.kimmking.cache.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    UserMapper userMapper;

    // @Cacheable(key = "#id", value = "userCache")
    @Override
    public User find(int id) {
        System.out.println(" ==> find " + id);
        return userMapper.find(id);
    }

    //(key="methodName",value="userCache")
    // @Cacheable
    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public Boolean lock(Integer id) {
        String reqId = IdUtil.fastSimpleUUID();
        String key = "lock::" + id;
        // return true;
        boolean lock = redisUtil.getLock(key, reqId, 20L);
        System.out.println(lock);
        if (!lock) return false;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean releaseLock = redisUtil.releaseLock(key, reqId);
        if (!releaseLock) System.err.println("解锁失败!!!");
        return true;
    }

    @Override
    public Boolean initStock() {
        redisTemplate.opsForValue().set("user::stock", 1000);
        return true;
    }

    @Override
    public Boolean stock() {
        String key = "user::stock";
        Long increment = redisTemplate.opsForValue().increment(key, -1);
        if (increment < 0) {
            redisTemplate.opsForValue().increment(key, 1);
            return false;
        }
        return true;
    }

    @Override
    public void pub() {
            redisTemplate.convertAndSend("channel", "message");
    }
}
