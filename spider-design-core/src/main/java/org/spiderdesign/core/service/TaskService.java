package org.spiderdesign.core.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.spiderdesign.core.mapper.TaskMapper;
import org.spiderdesign.core.model.Task;
import org.springframework.stereotype.Service;

@Service
public class TaskService extends ServiceImpl<TaskMapper, Task> {

}
