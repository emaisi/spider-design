package org.spiderflow.redis.web;

import java.util.List;

import org.spiderflow.common.CURDController;
import org.spiderflow.executor.PluginConfig;
import org.spiderflow.model.JsonBean;
import org.spiderflow.model.Plugin;
import org.spiderflow.redis.mapper.RedisSourceMapper;
import org.spiderflow.redis.model.RedisSource;
import org.spiderflow.redis.service.RedisSourceService;
import org.spiderflow.redis.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

@RestController
@RequestMapping("/redis")
public class RedisController extends CURDController<RedisSourceService,RedisSourceMapper, RedisSource> implements PluginConfig{
	
	@Autowired
	private RedisSourceService redisSourceService;

	@RequestMapping("/test")
	public JsonBean<String> test(String host,Integer port,String password,Integer database){
		try {
			StringRedisTemplate redisTemplate = RedisUtils.createRedisTemplate(host, port, password, database, 1, 1, 0);
			ValueOperations<String, String> operation = redisTemplate.opsForValue();
			String testKey = "____spider_flow_redis_test";
			operation.set(testKey, "1");
			if (redisTemplate.hasKey(testKey)) {
				redisTemplate.delete(testKey);
				return new JsonBean<String>(1,"测试成功");
			}
			return new JsonBean<String>(0,"测试失败");
		} catch (Exception e) {
			e.printStackTrace();
			return new JsonBean<String>(-1,e.getMessage());
		}
	}
	
	@RequestMapping("/all")
	public JsonBean<List<RedisSource>> all(){
		return new JsonBean<List<RedisSource>>(redisSourceService.list(new QueryWrapper<RedisSource>().select("id","name").orderByDesc("createDate")));
	}

	@Override
	public Plugin plugin() {
		return new Plugin("Redis数据源", "redis.html");
	}
}
