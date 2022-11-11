package org.spiderflow.oss.function.extension;

import org.spiderflow.executor.FunctionExtension;
import org.spiderflow.oss.model.Oss;
import org.spiderflow.oss.model.OssFile;
import org.spiderflow.oss.service.OssService;
import org.spiderflow.oss.utils.OSSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OssBytesFunctionExtension implements FunctionExtension{
	
	private static OssService ossService;
	
	@Autowired
	private void setOssMapper(OssService ossService) {
		OssBytesFunctionExtension.ossService = ossService;
	}

	@Override
	public Class<?> support() {
		return Object[].class;
	}
	
	public static OssFile ossUpload(Object[] objects,String ossId,String filePath) throws Exception {
		byte[] bytes = new byte[objects.length];
		for (int i = 0; i < objects.length; i++) {
			bytes[i] = (byte)objects[i];
		}
		Oss oss = ossService.getOss(ossId);
		if(null != oss) {
			return OSSUtil.uploadFileToOss(oss, bytes, filePath);
		}
		return null;
	}

}