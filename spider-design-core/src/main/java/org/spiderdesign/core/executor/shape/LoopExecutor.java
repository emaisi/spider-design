package org.spiderdesign.core.executor.shape;

import java.util.Map;

import org.spiderdesign.context.SpiderContext;
import org.spiderdesign.executor.ShapeExecutor;
import org.spiderdesign.model.SpiderNode;
import org.springframework.stereotype.Component;

/**
 * 循环执行器
 * @author Administrator
 *
 */
@Component
public class LoopExecutor implements ShapeExecutor{

	public static final String LOOP_ITEM = "loopItem";

	public static final String LOOP_START = "loopStart";

	public static final String LOOP_END = "loopEnd";

	@Override
	public void execute(SpiderNode node, SpiderContext context, Map<String,Object> variables) {
	}

	@Override
	public String supportShape() {
		return "loop";
	}
}
