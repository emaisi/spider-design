package org.spiderdesign.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.spiderdesign.core.model.Task;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {
}
