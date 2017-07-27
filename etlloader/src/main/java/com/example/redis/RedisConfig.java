package com.example.redis;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.alibaba.fastjson.JSON;
import com.example.utils.Constants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 开启缓存 把需要缓存的数据存到redis 实现分布式系统公用缓存
 * 也可与shiro配合redis做缓存，shiro做权限和认证
 * session用springsession替换，目前系统只需要认证session是否有效，不需要实例化该类
 * @author yingjie.chen
 *
 */
//@Configuration
//@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{

//	private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);  
    
	    @Value("${spring.redis.host}")
	    private String host;
	    @Value("${spring.redis.port}")
	    private int port;
	    @Value("${spring.redis.timeout}")
	    private int timeout;

	    @Bean
	    public KeyGenerator wiselyKeyGenerator(){
	        return new KeyGenerator() {
				@Override
				public Object generate(Object o, Method method, Object... objects) {
					StringBuilder sb = new StringBuilder(Constants.CACHE_NAMESPACE);
					CacheConfig cacheConfig = o.getClass().getAnnotation(CacheConfig.class);
					Cacheable cacheable = method.getAnnotation(Cacheable.class);
					CachePut cachePut = method.getAnnotation(CachePut.class);
					CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
					if (cacheable != null) {
						String[] cacheNames = cacheable.value();
						if (cacheNames != null && cacheNames.length > 0) {
							sb.append(cacheNames[0]);
						}
					} else if (cachePut != null) {
						String[] cacheNames = cachePut.value();
						if (cacheNames != null && cacheNames.length > 0) {
							sb.append(cacheNames[0]);
						}
					} else if (cacheEvict != null) {
						String[] cacheNames = cacheEvict.value();
						if (cacheNames != null && cacheNames.length > 0) {
							sb.append(cacheNames[0]);
						}
					}
					if (cacheConfig != null && sb.toString().equals(Constants.CACHE_NAMESPACE)) {
						String[] cacheNames = cacheConfig.cacheNames();
						if (cacheNames != null && cacheNames.length > 0) {
							sb.append(cacheNames[0]);
						}
					}
					if (sb.toString().equals(Constants.CACHE_NAMESPACE)) {
						sb.append(o.getClass().getName());
					}
					sb.append(":");
					if (objects != null) {
						if (objects.length == 1) {
							if (objects[0] instanceof Number || objects[0] instanceof String
									|| objects[0] instanceof Boolean) {
								sb.append(objects[0]);
							} else {
								try {
									sb.append(objects[0].getClass().getMethod("getId").invoke(objects[0]));
								} catch (Exception e) {
									if (objects[0] instanceof Map && ((Map<?, ?>) objects[0]).get("id") != null) {
										sb.append(((Map<?, ?>) objects[0]).get("id"));
									} else {
										sb.append(JSON.toJSON(objects[0]));
									}
								}
							}
						} else {
							sb.append(StringUtils.join(objects, ","));
						}
					} else {
						sb.append(method.getName());
					}
					return sb.toString();
				}
			};
	    }

	    @Bean
	    public JedisConnectionFactory redisConnectionFactory(){
	        JedisConnectionFactory factory = new JedisConnectionFactory();
	        factory.setHostName(host);
	        factory.setPort(port);
	        factory.setTimeout(timeout);    //设置连接超时
	        return factory;
	    }

	    @Bean
	    public CacheManager cacheManager(RedisTemplate redisTemplate){
	        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
	        cacheManager.setDefaultExpiration(1800);  //设置 key-value 超时时间
	        return cacheManager;
	    }

	    @Bean
	    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
	        StringRedisTemplate template = new StringRedisTemplate(factory);
	        setSerializer(template);    //设置序列化工具，就不必实现Serializable接口
	        template.afterPropertiesSet();
	        return template;
	    }

	    @SuppressWarnings({ "rawtypes", "unchecked" })
		private void setSerializer(StringRedisTemplate template){
	        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
	        ObjectMapper om = new ObjectMapper();
	        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
	        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	        jackson2JsonRedisSerializer.setObjectMapper(om);
	        template.setValueSerializer(jackson2JsonRedisSerializer);
	    }
}
