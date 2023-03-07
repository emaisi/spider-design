package org.spiderdesign.core.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.spiderdesign.core.model.DataSource;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface DataSourceMapper extends BaseMapper<DataSource>{

	@Select("select id,name from sp_datasource")
	List<DataSource> selectAll();

}
