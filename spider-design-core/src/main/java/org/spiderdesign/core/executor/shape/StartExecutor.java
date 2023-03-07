package org.spiderdesign.core.executor.shape;

import java.util.Map;

import org.spiderdesign.context.SpiderContext;
import org.spiderdesign.executor.ShapeExecutor;
import org.spiderdesign.model.SpiderNode;
import org.springframework.stereotype.Component;

/**
 * 开始执行器
 * @author Administrator
 *
 */
@Component
public class StartExecutor implements ShapeExecutor{

	@Override
	public void execute(SpiderNode node, SpiderContext context, Map<String,Object> variables) {

	}

	@Override
	public String supportShape() {
		return "start";
	}

}
