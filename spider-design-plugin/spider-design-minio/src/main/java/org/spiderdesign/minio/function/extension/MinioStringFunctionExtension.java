package org.spiderdesign.minio.function.extension;

import org.jsoup.Jsoup;
import org.spiderdesign.executor.FunctionExtension;
import org.spiderdesign.minio.model.Minio;
import org.spiderdesign.minio.model.MinioFile;
import org.spiderdesign.minio.utils.MinioUtil;
import org.spiderdesign.minio.service.MinioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// TODO
@Component
public class MinioStringFunctionExtension implements FunctionExtension{

	private static MinioService minioService;

	@Autowired
	private void setMinioMapper(MinioService minioService) {
		MinioStringFunctionExtension.minioService = minioService;
	}

	@Override
	public Class<?> support() {
		return String.class;
	}

	public static MinioFile minioUpload(String url, String minioId, String filePath) throws Exception {
		Minio minio = minioService.getMinio(minioId);
		if(null != minio) {
			return MinioUtil.uploadFileToMinio(minio, Jsoup.connect(url).ignoreContentType(true).execute().bodyAsBytes(), filePath);
		}
		return null;
	}

	public static void minioDelete(String filePath,String minioId) {
		//MinioUtil.deleteFile(minioService.getMinio(minioId), filePath);
	}

}
