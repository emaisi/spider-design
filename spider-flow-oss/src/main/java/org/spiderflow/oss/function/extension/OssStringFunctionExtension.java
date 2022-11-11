package org.spiderflow.oss.function.extension;

import org.jsoup.Jsoup;
import org.spiderflow.executor.FunctionExtension;
import org.spiderflow.oss.model.Oss;
import org.spiderflow.oss.model.OssFile;
import org.spiderflow.oss.service.OssService;
import org.spiderflow.oss.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OssStringFunctionExtension implements FunctionExtension{
	
	private static OssService ossService;
	
	@Autowired
	private void setOssMapper(OssService ossService) {
		OssStringFunctionExtension.ossService = ossService;
	}

	@Override
	public Class<?> support() {
		return String.class;
	}
	
	public static OssFile ossUpload(String url,String ossId,String filePath) throws Exception {
		Oss oss = ossService.getOss(ossId);
		if(null != oss) {
			return OSSUtil.uploadFileToOss(oss, Jsoup.connect(url).ignoreContentType(true).execute().bodyAsBytes(), filePath);
		}
		return null;
	}
	
	public static void ossDelete(String filePath,String ossId) {
		OSSUtil.deleteFile(ossService.getOss(ossId), filePath);
	}

}
