package org.spiderflow.oss.function.extension;

import org.spiderflow.executor.PluginConfig;
import org.spiderflow.model.Plugin;
import org.springframework.stereotype.Component;

@Component
public class OssConfig implements PluginConfig{

	@Override
	public Plugin plugin() {
		return new Plugin("OSS配置", "resources/templates/ossList.html");
	}
	
}