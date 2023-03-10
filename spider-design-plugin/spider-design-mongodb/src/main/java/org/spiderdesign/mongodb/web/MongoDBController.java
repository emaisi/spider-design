package org.spiderdesign.mongodb.web;

import org.spiderdesign.common.CURDController;
import org.spiderdesign.executor.PluginConfig;
import org.spiderdesign.model.JsonBean;
import org.spiderdesign.model.Plugin;
import org.spiderdesign.mongodb.mapper.MongoDataSourceMapper;
import org.spiderdesign.mongodb.model.MongoDataSource;
import org.spiderdesign.mongodb.service.MongoDataSourceService;
import org.spiderdesign.mongodb.utils.MongoDBUtils;
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
