package org.spiderdesign.core.executor.shape;

import org.spiderdesign.context.SpiderContext;
import org.spiderdesign.executor.ShapeExecutor;
import org.spiderdesign.model.SpiderNode;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CommentExecutor implements ShapeExecutor{

	@Override
	public void execute(SpiderNode node, SpiderContext context, Map<String,Object> variables) {

	}

	@Override
	public String supportShape() {
		return "comment";
	}

}
