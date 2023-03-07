package org.spiderdesign.oss.function.extension;

import org.jsoup.Jsoup;
import org.spiderdesign.executor.FunctionExtension;
import org.spiderdesign.oss.model.Oss;
import org.spiderdesign.oss.model.OssFile;
import org.spiderdesign.oss.service.OssService;
import org.spiderdesign.oss.utils.OSSUtil;
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
