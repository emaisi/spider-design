package org.spiderdesign.minio.controller;

import org.spiderdesign.common.CURDController;
import org.spiderdesign.minio.mapper.MinioMapper;
import org.spiderdesign.minio.model.Minio;
import org.spiderdesign.minio.service.MinioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("minio")
public class MinioController extends CURDController<MinioService, MinioMapper, Minio> {
	//  TODO
}
