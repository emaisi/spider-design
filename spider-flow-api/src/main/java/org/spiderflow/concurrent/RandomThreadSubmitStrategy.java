package org.spiderflow.concurrent;

import cn.hutool.core.util.RandomUtil;
import org.spiderflow.model.SpiderNode;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RandomThreadSubmitStrategy implements ThreadSubmitStrategy{

    private List<SpiderFutureTask<?>> taskList = new CopyOnWriteArrayList<>();

    @Override
    public Comparator<SpiderNode> comparator() {
        return (o1, o2) -> RandomUtil.randomInt(0,3) - 1;
    }

    @Override
    public void add(SpiderFutureTask<?> task) {
        taskList.add(task);
    }

    @Override
    public boolean isEmpty() {
        return taskList.isEmpty();
    }

    @Override
    public SpiderFutureTask<?> get() {
        return taskList.remove(RandomUtil.randomInt(0, taskList.size()));
    }
}
