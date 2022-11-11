package org.spiderflow.oss.controller;

import org.spiderflow.common.CURDController;
import org.spiderflow.oss.mapper.OssMapper;
import org.spiderflow.oss.model.Oss;
import org.spiderflow.oss.service.OssService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oss")
public class OssController extends CURDController<OssService, OssMapper, Oss> {
	
}