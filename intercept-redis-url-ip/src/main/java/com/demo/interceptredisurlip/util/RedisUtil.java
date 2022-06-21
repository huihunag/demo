package com.demo.interceptredisurlip.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @description RedisUtil
 * @Date 2022/6/20 21:06
 * @Author HUANGXINWEI
 */
@Component
@Slf4j
public class RedisUtil {

    private static final Long SUCCESS = 1L;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    // =============================common============================

    /**
     * 获取锁
     * @param lockKey
     * @param value
     * @param expireTime：单位-秒
     * @return
     */
    public boolean getLock(String lockKey, Object value, int expireTime) {
        try {
            log.info("添加分布式锁key={},expireTime={}",lockKey,expireTime);
            String script = "if redis.call('setNx',KEYS[1],ARGV[1]) then if redis.call('get',KEYS[1])==ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end end";
            RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
            Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), value, expireTime);
            if (SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 释放锁
     * @param lockKey
     * @param value
     * @return
     */
    public boolean releaseLock(String lockKey, String value) {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        RedisScript<String> redisScript = new DefaultRedisScript<>(script, String.class);
        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), value);
        if (SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }

    /**
     * 判断key是否存在
     * @param lockKey
     * @return
     */
    public boolean hasKey(String lockKey) {
        if(redisTemplate.hasKey(lockKey)){
            return true;
        }
        return false;
    }

    /**
     * 增加接口访问次数
     * @param lockKey
     * @return
     */
    public long incr(String lockKey, long i) {
        if(redisTemplate.hasKey(lockKey)){
            Object obj = redisTemplate.opsForValue().get(lockKey);
            if (obj != null){
                i += (Integer)obj;
                redisTemplate.opsForValue().set(lockKey, i);
            }
        }
        return i;
    }

}
