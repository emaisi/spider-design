package org.spiderflow.redis.service;

import java.io.Serializable;

import org.spiderflow.redis.executor.function.RedisFunctionExecutor;
import org.spiderflow.redis.mapper.RedisSourceMapper;
import org.spiderflow.redis.model.RedisSource;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class RedisSourceService extends ServiceImpl<RedisSourceMapper, RedisSource>{
	
	@Override
	public boolean saveOrUpdate(RedisSource entity) {
		if(entity.getId() != null){
			removeOldRedisTemplate(entity.getId());
		}
		return super.saveOrUpdate(entity);
	}
	
	@Override
	public boolean removeById(Serializable id) {
		removeOldRedisTemplate(id);
		return super.removeById(id);
	}
	
	private void removeOldRedisTemplate(Serializable id){
		RedisSource oldSource = getById(id);
		if(oldSource != null){
			RedisTemplate<?, ?> template = RedisFunctionExecutor.REDIS_CACHED_TEMPLATE.get(oldSource.getAlias());
			if(template != null){
				JedisConnectionFactory connectionFactory =  (JedisConnectionFactory) template.getConnectionFactory();
				connectionFactory.destroy();
			}
			RedisFunctionExecutor.REDIS_CACHED_TEMPLATE.remove(oldSource.getAlias());
		}
	}

}
