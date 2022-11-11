package org.spiderflow.mongodb.executor.function;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;
import org.spiderflow.annotation.Comment;
import org.spiderflow.executor.FunctionExecutor;
import org.spiderflow.mongodb.model.MongoDataSource;
import org.spiderflow.mongodb.service.MongoDataSourceService;
import org.spiderflow.mongodb.utils.MongoDBUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;

@Component
@Comment("mongodb相关操作")
public class MongoFunctionExecutor extends HashMap<String, Object> implements FunctionExecutor{

	private static final long serialVersionUID = -4687332980694325662L;
	
	public final static Map<String, MongoClient> MONGO_CLIENTS = new HashMap<>();
	
	public final static Map<String, String> MONGO_DATABASE = new HashMap<>();
	
	private static MongoDataSourceService mongoDataSourceService;

	@Override
	public String getFunctionPrefix() {
		return "mongodb";
	}
	
	private synchronized static MongoClient findMongoClient(String alias){
		MongoClient client = MONGO_CLIENTS.get(alias);
		if(client == null){
			MongoDataSource mongoDataSource = mongoDataSourceService.getOne(new QueryWrapper<MongoDataSource>().eq("alias", alias).orderByDesc("create_date"));
			if(mongoDataSource == null){
				throw new NullPointerException("找不到Mongo数据源'" + alias +"'");
			}
			client = MongoDBUtils.createMongoClient(mongoDataSource);
			MONGO_CLIENTS.put(alias, client);
			MONGO_DATABASE.put(alias, mongoDataSource.getDatabase());
		}
		return client;
	}
	
	@Override
	public Object get(Object key) {
		String alias = key == null ? null : key.toString();
		MongoClient client = findMongoClient(alias);
		return new HashMap<String,MongoCollection<Document>>(){

			private static final long serialVersionUID = -1125352602286324297L;
			
			@Override
			public MongoCollection<Document> get(Object key) {
				if(key == null){
					throw new NullPointerException("collection不能为空");
				}
				return client.getDatabase(MONGO_DATABASE.get(alias)).getCollection(key.toString());
			}
			
		};
	}

	@Autowired
	public void setMongoDataSourceService(MongoDataSourceService mongoDataSourceService) {
		MongoFunctionExecutor.mongoDataSourceService = mongoDataSourceService;
	}
	
	
	
}
