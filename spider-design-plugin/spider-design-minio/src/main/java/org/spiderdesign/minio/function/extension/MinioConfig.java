package org.spiderdesign.minio.function.extension;

import org.spiderdesign.executor.PluginConfig;
import org.spiderdesign.model.Plugin;
import org.springframework.stereotype.Component;

// TODO
@Component
public class MinioConfig implements PluginConfig{

	@Override
	public Plugin plugin() {
		return new Plugin("MinIO配置", "resources/templates/minioList.html");
	}

}
