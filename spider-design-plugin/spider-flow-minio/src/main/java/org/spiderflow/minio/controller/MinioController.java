package org.spiderflow.minio.controller;

import org.spiderflow.common.CURDController;
import org.spiderflow.minio.mapper.MinioMapper;
import org.spiderflow.minio.model.Minio;
import org.spiderflow.minio.service.MinioService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("minio")
public class MinioController extends CURDController<MinioService, MinioMapper, Minio> {
	//  TODO
}