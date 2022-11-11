package org.spiderflow.mongodb.executor.function.extension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.spiderflow.annotation.Example;
import org.spiderflow.executor.FunctionExtension;
import org.springframework.stereotype.Component;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;

@Component
public class MongoFindIterableExtension implements FunctionExtension{

	@Override
	public Class<?> support() {
		return FindIterable.class;
	}
	
	@Example("${mongodb.aliasName.collectionName.find({key : value}).list()}")
	public static List<Map<?,?>> list(FindIterable<Document> iterable){
		MongoCursor<Document> cursor = iterable.iterator();
		List<Map<?,?>> result = new ArrayList<>();
		while(cursor.hasNext()){
			result.add(cursor.next());
		}
		return result;
	}

}
