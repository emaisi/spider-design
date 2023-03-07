package org.spiderdesign.concurrent;

import java.util.concurrent.FutureTask;
import org.spiderdesign.concurrent.SpiderFlowThreadPoolExecutor.SubThreadPoolExecutor;
import org.spiderdesign.model.SpiderNode;

public class SpiderFutureTask<V> extends FutureTask {

    private SubThreadPoolExecutor executor;

    private SpiderNode node;

    public SpiderFutureTask(Runnable runnable, V result, SpiderNode node,SubThreadPoolExecutor executor) {
        super(runnable,result);
        this.executor = executor;
        this.node = node;
    }

    public SubThreadPoolExecutor getExecutor() {
        return executor;
    }

    public SpiderNode getNode() {
        return node;
    }
}
