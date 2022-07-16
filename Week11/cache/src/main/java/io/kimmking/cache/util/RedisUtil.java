package io.kimmking.cache.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 * @author 89014542
 * @date 2021/7/9
 * Description
 */
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    private DefaultRedisScript<Long> redisScript;

    private RedisSerializer<String> argsSerializer;

    private RedisSerializer resultSerializer;

    private final Long EXEC_RESULT = 1L;

    @PostConstruct
    public void init() {
        redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        argsSerializer = new StringRedisSerializer();
        resultSerializer = new StringRedisSerializer();
    }

    public boolean releaseLock(String key, String requestId) {
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/releaseLock.lua")));
        Object result = redisTemplate.execute(redisScript, argsSerializer, resultSerializer, Collections.singletonList(key), requestId);
        return EXEC_RESULT.equals(result);
    }

    public boolean getLock(String key, String requestId, long expireTime) {
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/getLock.lua")));
        Object result = redisTemplate.execute(redisScript, argsSerializer, resultSerializer, Collections.singletonList(key), requestId, String.valueOf(expireTime));
        return EXEC_RESULT.equals(result);
    }
}
