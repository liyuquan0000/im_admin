package com.jdd.imadmin.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis缓存配置类
 */

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "Duplicates", "unchecked"})
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig extends CachingConfigurerSupport {

	//还是使用springboot默认配置的RedisConnectionFactory
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	// 默认用的是用JdkSerializationRedisSerializer进行序列化的
	@Bean(name = "redisTemplate")
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public StringRedisTemplate redisTemplate() {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		// 注入数据源
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		// 使用Jackson2JsonRedisSerialize 替换默认序列化
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

		// key-value结构序列化数据结构
		redisTemplate.setKeySerializer(stringRedisSerializer);
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		// hash数据结构序列化方式,必须这样否则存hash 就是基于jdk序列化的
		redisTemplate.setHashKeySerializer(stringRedisSerializer);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

		// 启用默认序列化方式
		redisTemplate.setEnableDefaultSerializer(true);
		redisTemplate.setDefaultSerializer(jackson2JsonRedisSerializer);

		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}
