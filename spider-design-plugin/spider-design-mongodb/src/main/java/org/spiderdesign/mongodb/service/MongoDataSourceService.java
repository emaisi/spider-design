package org.spiderdesign.mongodb.service;

import java.io.Serializable;

import org.spiderdesign.mongodb.executor.function.MongoFunctionExecutor;
import org.spiderdesign.mongodb.mapper.MongoDataSourceMapper;
import org.spiderdesign.mongodb.model.MongoDataSource;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mongodb.MongoClient;

@Service
public class MongoDataSourceService extends ServiceImpl<MongoDataSourceMapper, MongoDataSource>{

	@Override
	public boolean saveOrUpdate(MongoDataSource entity) {
		if(entity.getId() != null){
			removeOldMongoDataSource(entity.getId());
		}
		return super.saveOrUpdate(entity);
	}
	@Override
	public boolean removeById(Serializable id) {
		removeOldMongoDataSource(id);
		return super.removeById(id);
	}

	private void removeOldMongoDataSource(Serializable id){
		MongoDataSource oldSource = getById(id);
		if(oldSource != null){
			MongoClient client = MongoFunctionExecutor.MONGO_CLIENTS.get(oldSource.getAlias());
			if(client != null){
				try {
					client.close();
				} catch (Exception e) {
				}
			}
			MongoFunctionExecutor.MONGO_CLIENTS.remove(oldSource.getAlias());
			MongoFunctionExecutor.MONGO_DATABASE.remove(oldSource.getAlias());
		}
	}

}
