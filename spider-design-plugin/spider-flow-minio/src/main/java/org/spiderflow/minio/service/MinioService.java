package org.spiderflow.minio.service;

import org.apache.commons.lang3.StringUtils;
import org.spiderflow.minio.model.Minio;
import org.spiderflow.minio.mapper.MinioMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class MinioService extends ServiceImpl<MinioMapper, Minio> {
	
	public Minio getMinio(String minioId) {
		Minio minio = null;
		if(StringUtils.isNotBlank(minioId)) {
			minio = getBaseMapper().selectById(minioId);
		}else {
			minio = getBaseMapper().selectOne(new QueryWrapper<Minio>().orderByDesc("create_date"));
		}
		return minio;
	}
	
}
