package org.spiderflow.minio.function;

import org.apache.commons.lang.StringUtils;
import org.spiderflow.annotation.Comment;
import org.spiderflow.annotation.Example;
import org.spiderflow.executor.FunctionExecutor;
import org.spiderflow.minio.model.Minio;
import org.spiderflow.minio.service.MinioService;
import org.spiderflow.minio.utils.MD5Util;
import org.spiderflow.minio.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * minio 工具类上传
 */
@Component
public class MinioFunctionExecutor implements FunctionExecutor{

	private static MinioService minioService;

	@Autowired
	private void setMinioMapper(MinioService minioService) {
		MinioFunctionExecutor.minioService = minioService;
	}

	@Override
	public String getFunctionPrefix() {
		return "minio";
	}

	@Comment("上传网络图片（url）到minio")
	@Example("${minio.upload('http://www.mimukeji.cn/portal/themes/img/mimukeji-wechat-358-358.jpg','1')}")
	public static String upload(String url, String minioId) throws Exception {
		String returnValue = url;
		try{
			 Minio minio = minioService.getMinio(minioId);

			if(null != minio) {
				String originUrl = url;
				String suffix = "";

				if(originUrl.startsWith("//")){
					originUrl = "http:"+originUrl;
				}
				if(originUrl.contains("png")){
					suffix = ".png";
				}else if(originUrl.contains("jpeg")){
					suffix = ".jpeg";
				}else if(originUrl.contains("webp")){
					suffix = ".webp";
				}else {
					suffix = ".jpg";
				}

				String destObjectName = minio.getFilePath() + "/" + MD5Util.getMD5String(url)+ suffix;
				MinioUtil.uploadByNetworkStream(MinioUtil.getMinioClient(minio),url,minio.getBucketName(),destObjectName);
				returnValue = "/" + minio.getBucketName() + destObjectName;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnValue;
	}

	@Comment("上传网络图片（url list）到minio")
	public static List<String> upload(List<String> urlList, String minioId) throws Exception {
		List<String> returnList = new ArrayList<String>();
		try{
			 Minio minio = minioService.getMinio(minioId);

			if(null != minio && null != urlList && urlList.size() > 0) {
				urlList.forEach(url ->{
					if(null != url && StringUtils.isNotEmpty(url.trim())){
						String originUrl = url;
						String suffix = "";

						if(originUrl.startsWith("//")){
							originUrl = "http:"+originUrl;
						}
						if(originUrl.contains("png")){
							suffix = ".png";
						}else if(originUrl.contains("jpeg")){
							suffix = ".jpeg";
						}else if(originUrl.contains("webp")){
							suffix = ".webp";
						}else {
							suffix = ".jpg";
						}

						String destObjectName = minio.getFilePath() + "/" + MD5Util.getMD5String(url)+ suffix;
						MinioUtil.uploadByNetworkStream(MinioUtil.getMinioClient(minio),url,minio.getBucketName(),destObjectName);
						returnList.add("/" + minio.getBucketName() + destObjectName);
					}
				});
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnList;
	}
}
