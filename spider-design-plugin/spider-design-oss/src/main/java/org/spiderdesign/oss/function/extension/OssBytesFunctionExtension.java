package org.spiderdesign.oss.function.extension;

import org.spiderdesign.executor.FunctionExtension;
import org.spiderdesign.oss.model.Oss;
import org.spiderdesign.oss.model.OssFile;
import org.spiderdesign.oss.service.OssService;
import org.spiderdesign.oss.utils.OSSUtil;
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
