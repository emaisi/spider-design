package org.spiderdesign.core.executor.shape;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spiderdesign.context.SpiderContext;
import org.spiderdesign.core.Spider;
import org.spiderdesign.core.model.SpiderFlow;
import org.spiderdesign.core.service.SpiderFlowService;
import org.spiderdesign.core.utils.SpiderFlowUtils;
import org.spiderdesign.executor.ShapeExecutor;
import org.spiderdesign.model.SpiderNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 子流程执行器
 * @author Administrator
 *
 */
@Component
public class ProcessExecutor implements ShapeExecutor{

	public static final String FLOW_ID = "flowId";

	private static Logger logger = LoggerFactory.getLogger(ProcessExecutor.class);

	@Autowired
	private SpiderFlowService spiderFlowService;

	@Autowired
	private Spider spider;

	@Override
	public void execute(SpiderNode node, SpiderContext context, Map<String,Object> variables) {
		String flowId = node.getStringJsonValue("flowId");
		SpiderFlow spiderFlow = spiderFlowService.getById(flowId);
		if(spiderFlow != null){
			logger.info("执行子流程:{}", spiderFlow.getName());
			SpiderNode root = SpiderFlowUtils.loadXMLFromString(spiderFlow.getXml());
			spider.executeNode(null,root,context,variables);
		}else{
			logger.info("执行子流程:{}失败，找不到该子流程", flowId);
		}
	}

	@Override
	public String supportShape() {
		return "process";
	}

}
