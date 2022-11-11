package org.spiderflow.mongodb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.spiderflow.mongodb.model.MongoDataSource;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface MongoDataSourceMapper extends BaseMapper<MongoDataSource>{

}
