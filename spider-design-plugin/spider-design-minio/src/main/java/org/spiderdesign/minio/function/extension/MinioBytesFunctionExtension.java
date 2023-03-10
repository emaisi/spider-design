package org.spiderdesign.minio.function.extension;

import org.spiderdesign.executor.FunctionExtension;
import org.spiderdesign.minio.model.Minio;
import org.spiderdesign.minio.model.MinioFile;
import org.spiderdesign.minio.service.MinioService;
import org.spiderdesign.minio.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// TODO
@Component
public class MinioBytesFunctionExtension implements FunctionExtension{

	private static MinioService minioService;

	@Autowired
	private void setMinioMapper(MinioService minioService) {
		MinioBytesFunctionExtension.minioService = minioService;
	}

	@Override
	public Class<?> support() {
		return Object[].class;
	}

	public static MinioFile minioUpload(Object[] objects, String minioId, String filePath) throws Exception {
		byte[] bytes = new byte[objects.length];
		for (int i = 0; i < objects.length; i++) {
			bytes[i] = (byte)objects[i];
		}
		Minio minio = minioService.getMinio(minioId);
		if(null != minio) {
			return MinioUtil.uploadFileToMinio(minio, bytes, filePath);
		}
		return null;
	}

}
