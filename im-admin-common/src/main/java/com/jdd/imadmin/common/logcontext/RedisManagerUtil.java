package com.jdd.imadmin.common.logcontext;

import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author zhangdi
 * @date 2018/3/13
 * @Description
 */
public class RedisManagerUtil {
    private static RedisManagerUtil instance;
    private RedisTemplate<String, Object> redisTemplate = null;

    public static RedisManagerUtil getInstance(String host, int port, String pass) {
        if (instance == null) {
            synchronized(RedisManagerUtil.class) {
                if (instance == null) {
                    instance = new RedisManagerUtil(host, port, pass);
                }
            }
        }
        return instance;
    }

    private RedisManagerUtil(String host, int port, String pass) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setDatabase(0);

        JedisClientConfiguration.JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        JedisConnectionFactory factory = new JedisConnectionFactory(redisStandaloneConfiguration,
                jedisClientConfiguration.build());

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(factory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setEnableDefaultSerializer(true);
        redisTemplate.setDefaultSerializer(stringRedisSerializer);
        redisTemplate.afterPropertiesSet();
        this.redisTemplate = redisTemplate;
    }


    public Long rpushNoLog(String key, String strings) {
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.rightPush(key, strings);
    }


}
