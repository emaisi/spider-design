package org.spiderflow.mongodb.web;

import org.spiderflow.common.CURDController;
import org.spiderflow.executor.PluginConfig;
import org.spiderflow.model.JsonBean;
import org.spiderflow.model.Plugin;
import org.spiderflow.mongodb.mapper.MongoDataSourceMapper;
import org.spiderflow.mongodb.model.MongoDataSource;
import org.spiderflow.mongodb.service.MongoDataSourceService;
import org.spiderflow.mongodb.utils.MongoDBUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoIterable;

@RestController
@RequestMapping("/mongo")
public class MongoDBController extends CURDController<MongoDataSourceService, MongoDataSourceMapper, MongoDataSource> implements PluginConfig{
	
	@RequestMapping("/test")
	public JsonBean<String> test(MongoDataSource dataSource){
		
		try(MongoClient client = MongoDBUtils.createMongoClient(dataSource)){
			MongoIterable<String> dbs = client.listDatabaseNames();
			dbs.first();
			return new JsonBean<String>(1, "测试成功");
		} catch (Exception e) {
			return new JsonBean<String>(0, e.getMessage());
		}	
	}

	@Override
	public Plugin plugin() {
		return new Plugin("Mongo数据源", "mongo.html");
	}
	
}
