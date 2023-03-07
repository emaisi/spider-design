package org.spiderdesign.mongodb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.spiderdesign.mongodb.model.MongoDataSource;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Mapper
public interface MongoDataSourceMapper extends BaseMapper<MongoDataSource>{

}
