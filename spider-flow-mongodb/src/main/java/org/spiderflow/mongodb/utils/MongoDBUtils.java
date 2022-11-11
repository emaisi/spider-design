package org.spiderflow.mongodb.utils;

import org.apache.commons.lang3.StringUtils;
import org.spiderflow.mongodb.model.MongoDataSource;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoDBUtils {
	
	public static MongoClient createMongoClient(MongoDataSource dataSource){
		ServerAddress address = new ServerAddress(dataSource.getHost(), dataSource.getPort());
		Builder options = new MongoClientOptions.Builder();
		options.connectTimeout(5000)
			.maxWaitTime(3000)
			.serverSelectionTimeout(3000);
		if(StringUtils.isNotBlank(dataSource.getUsername()) && StringUtils.isNotBlank(dataSource.getPassword())){
			MongoCredential credential = MongoCredential.createScramSha1Credential(dataSource.getUsername(),dataSource.getDatabase(),dataSource.getPassword().toCharArray());
			return new MongoClient(address, credential, options.build());
		}else{
			return new MongoClient(address, options.build());
		}
	}
	
}
