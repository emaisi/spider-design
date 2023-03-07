package org.spiderdesign.oss.controller;

import org.spiderdesign.common.CURDController;
import org.spiderdesign.oss.mapper.OssMapper;
import org.spiderdesign.oss.model.Oss;
import org.spiderdesign.oss.service.OssService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("oss")
public class OssController extends CURDController<OssService, OssMapper, Oss> {

}
