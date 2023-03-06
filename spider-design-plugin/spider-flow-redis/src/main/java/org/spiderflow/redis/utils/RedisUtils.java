package org.spiderflow.redis.utils;

import org.spiderflow.redis.model.RedisSource;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {
	
	public static StringRedisTemplate createRedisTemplate(String host,Integer port,String password,Integer database,Integer poolMaxActive,Integer poolMaxIdle,Integer poolMinIdle){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(poolMaxActive == null ? 8 : poolMaxActive);
		poolConfig.setMaxIdle(poolMaxIdle == null ? 8 : poolMaxIdle);
		poolConfig.setMinIdle(poolMinIdle == null ? 0 : poolMinIdle);
		JedisClientConfiguration jedisConfigConfiguration = JedisClientConfiguration.builder().usePooling().poolConfig(poolConfig).build();
		RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
		standaloneConfiguration.setDatabase(database == null ? 0 : database);
		standaloneConfiguration.setHostName(host);
		standaloneConfiguration.setPassword(RedisPassword.of(password));
		standaloneConfiguration.setPort(port);
		StringRedisTemplate redisTemplate = new StringRedisTemplate(new JedisConnectionFactory(standaloneConfiguration,jedisConfigConfiguration));
		return redisTemplate;
	}
	
	public static StringRedisTemplate createRedisTemplate(RedisSource redisSource){
		return createRedisTemplate(redisSource.getHost(), redisSource.getPort(), redisSource.getPassword(), redisSource.getDbIndex(), redisSource.getMaxConnections(), redisSource.getMaxIdle(), redisSource.getMinIdle());
	}

}
