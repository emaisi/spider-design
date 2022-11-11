package org.spiderflow.minio.function.extension;

import org.spiderflow.executor.PluginConfig;
import org.spiderflow.model.Plugin;
import org.springframework.stereotype.Component;

// TODO
@Component
public class MinioConfig implements PluginConfig{

	@Override
	public Plugin plugin() {
		return new Plugin("MinIO配置", "resources/templates/minioList.html");
	}
	
}