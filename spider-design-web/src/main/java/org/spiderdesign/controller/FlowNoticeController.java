package org.spiderdesign.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spiderdesign.core.model.FlowNotice;
import org.spiderdesign.core.service.FlowNoticeService;
import org.spiderdesign.enums.FlowNoticeWay;
import org.spiderdesign.model.JsonBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flowNotice")
public class FlowNoticeController {

	private static final Logger logger = LoggerFactory.getLogger(FlowNoticeController.class);
	@Autowired
	private FlowNoticeService flowNoticeService;

	@RequestMapping("/save")
	public JsonBean<FlowNotice> save(FlowNotice entity) {
		if (StringUtils.isEmpty(entity.getId())) {
			return new JsonBean<FlowNotice>(0, "流程id不能为空");
		}
		try {
			flowNoticeService.saveOrUpdate(entity);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			return new JsonBean<FlowNotice>(0, e.getMessage() == null ? "发生错误" : e.getMessage());
		}
		return new JsonBean<FlowNotice>(entity);
	}

	@RequestMapping("/find")
	public JsonBean<FlowNotice> find(String id) {
		FlowNotice data = flowNoticeService.getById(id);
		if (data == null) {
			data = new FlowNotice();
			data.setId(id);
		}
		return new JsonBean<FlowNotice>(data);
	}

	@RequestMapping("/getNoticeWay")
	public JsonBean<Map<String, String>> getNoticeWay() {
		return new JsonBean<Map<String, String>>(FlowNoticeWay.getMap());
	}

}
