package org.spiderflow.redis.executor.function;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.spiderflow.annotation.Comment;
import org.spiderflow.annotation.Example;
import org.spiderflow.executor.FunctionExecutor;
import org.spiderflow.expression.DynamicMethod;
import org.spiderflow.redis.model.RedisSource;
import org.spiderflow.redis.service.RedisSourceService;
import org.spiderflow.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import redis.clients.util.SafeEncoder;

@Component
@Comment("redis常用方法")
public class RedisFunctionExecutor extends HashMap<String, Object> implements FunctionExecutor{
	
	private static final long serialVersionUID = 924273531304909956L;

	public final static Map<String,RedisTemplate<?, ?>> REDIS_CACHED_TEMPLATE = new HashMap<String, RedisTemplate<?,?>>();

	private static RedisSourceService redisSourceService;
	
	@Override
	public String getFunctionPrefix() {
		return "redis";
	}
	
	private static RedisTemplate<?,?> findRedisTemplate(String alias){
		RedisTemplate<?, ?> redisTemplate = REDIS_CACHED_TEMPLATE.get(alias);
		if(redisTemplate == null){
			RedisSource redisSource = redisSourceService.getOne(new QueryWrapper<RedisSource>().eq("alias", alias).orderByDesc("create_date"));
			if(redisSource == null){
				throw new NullPointerException("找不到Redis数据源'" + alias +"'");
			}
			redisTemplate = RedisUtils.createRedisTemplate(redisSource);
			REDIS_CACHED_TEMPLATE.put(alias, redisTemplate);
		}
		return redisTemplate;
	}
	
	private static byte[] serializer(RedisSerializer<String> stringSerializer,Object value){
		if(value == null || value instanceof String){
			return stringSerializer.serialize((String) value);
		}else if(value != null && value instanceof Number){
			return SafeEncoder.encode(String.valueOf(value));
		}
		return serializer(stringSerializer, value.toString());
	}
	
	private static Object deserialize(RedisSerializer<String> stringSerializer,Object value){
		if(value != null){
			if(value instanceof byte[]){
				return stringSerializer.deserialize((byte[]) value);
			}
			if(value instanceof List){
				@SuppressWarnings("unchecked")
				List<Object> valueList = (List<Object>) value;
				List<Object> resultList = new ArrayList<>(valueList.size());
				for (Object val : valueList) {
					resultList.add(deserialize(stringSerializer,val));
				}
				return resultList;
			}
		}
		return value;
	}
	
	@Override
	public Object get(Object key) {
		return use(key == null ? null : key.toString());
	}
	
	@Comment("选择数据源")
	@Example("${redis.use('aliasName')}或${redis.aliasName}")
	public static synchronized DynamicMethod use(String alias){
		return createDynamicMethod(findRedisTemplate(alias));
	}
	
	private static DynamicMethod createDynamicMethod(RedisTemplate<?, ?> redisTemplate){
		RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
		return new DynamicMethod() {
			
			@Override
			public Object execute(String methodName, List<Object> parameters) {
				return redisTemplate.execute(new RedisCallback<Object>() {

					@Override
					public Object doInRedis(RedisConnection connection) throws DataAccessException {
						byte[][] params = new byte[parameters.size()][];
						for (int i = 0; i < params.length; i++) {
							params[i] = serializer(stringSerializer,parameters.get(i));
						}
						return deserialize(stringSerializer,connection.execute(methodName, params));
					}
				});
			}
		};
	} 

	@Autowired
	public void setRedisSourceService(RedisSourceService redisSourceService) {
		RedisFunctionExecutor.redisSourceService = redisSourceService;
	}

}
