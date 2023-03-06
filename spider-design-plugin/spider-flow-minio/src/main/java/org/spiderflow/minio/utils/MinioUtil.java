package org.spiderflow.minio.utils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import org.spiderflow.minio.model.Minio;
import org.spiderflow.minio.model.MinioFile;

/**
 * author: https://gitee.com/lmyanglei
 */
public class MinioUtil {
	
	/**
	 * 
	 * @Title: getMinioClient
	 * @Description: 获取minio客户端
	 * @return MinioClient minio客户端
	 * @throws
	 */
	public static MinioClient getMinioClient(Minio minio) {
		return MinioClient.builder().endpoint(minio.getEndpoint()).credentials(minio.getAccessKey(), minio.getSecretKey())
				.build();
	}

	/**
	 * 
	 * @Title: uploadByNetworkStream 
	 * @Description: 通过网络流上传文件
	 * @param minioClient 	minio客户端
	 * @param url 			URL 
	 * @param bucketName 	bucket名称 
	 * @param objectName 	上传文件目录和（包括文件名）例如“test/index.html” 
	 * @return void 		返回类型
	 * @throws
	 */
	public static void uploadByNetworkStream(MinioClient minioClient, String url, String bucketName, String objectName) {
		try {
			InputStream inputStream = DownloadUtil.downloadAsStream(url, "","","","");
			Map<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "application/octet-stream");
			minioClient.putObject(
					PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
									inputStream, inputStream.available(), -1)
							.headers(headers)
							.build());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * 
	 * @Title: uploadByInputStream 
	 * @Description: 通过输入流上传文件
	 * @param minioClient 	minio客户端
	 * @param inputStream 	输入流 
	 * @param bucketName 	bucket名称
	 * @param objectName 	上传文件目录和（包括文件名） 例如“test/a.jpg” 
	 * @return void 		返回类型 
	 * @throws
	 */
	public static void uploadByInputStream(MinioClient minioClient, InputStream inputStream, String bucketName,
			String objectName) {
		try {
			Map<String, String> headers = new HashMap<>();
			headers.put("Content-Type", "application/octet-stream");
			ObjectWriteResponse objectWriteResponse = minioClient.putObject(
					PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
									inputStream, inputStream.available(), -1)
							.headers(headers)
							.build());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	/**
	 * 
	 * @Title: uploadByFile 
	 * @Description: 通过file上传文件 
	 * @param minioClient 	minio客户端
	 * @param file 			上传的文件 
	 * @param bucketName 	bucket名称
	 * @param objectName 	上传文件目录和（包括文件名） 例如“test/a.jpg” 
	 * @return void 		返回类型
	 * @throws
	 */
	public static void uploadByFile(MinioClient minioClient, File file, String bucketName, String objectName) {
		try {
			uploadByInputStream(minioClient,new FileInputStream(file),bucketName, objectName);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}
	
	/**
	 * 上传文件到minio
	 */
	public static MinioFile uploadFileToMinio(Minio minio, byte[] file, String filePath){
		MinioFile minioFile = new MinioFile();
		try {
	        MinioUtil.uploadByInputStream(getMinioClient(minio), new ByteArrayInputStream(file), minio.getBucketName(), filePath);
	        minioFile.setSize(file.length);
	        minioFile.setPath(filePath);
	        minioFile.setUrl("https://"+minio.getBucketName()+"."+minio.getEndpoint()+"/"+filePath);
	        String[] fps = filePath.split("/");
	        minioFile.setName(fps[fps.length-1]);
	        return minioFile;
		} catch (Exception e) {
			throw e;
		}
	}
}