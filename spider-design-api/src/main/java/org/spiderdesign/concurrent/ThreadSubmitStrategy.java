package org.spiderdesign.concurrent;

import org.spiderdesign.model.SpiderNode;

import java.util.Comparator;

public interface ThreadSubmitStrategy {

    Comparator<SpiderNode> comparator();

    void add(SpiderFutureTask<?> task);

    boolean isEmpty();

    SpiderFutureTask<?> get();
}
