package org.spiderdesign.oss.function.extension;

import org.spiderdesign.executor.PluginConfig;
import org.spiderdesign.model.Plugin;
import org.springframework.stereotype.Component;

@Component
public class OssConfig implements PluginConfig{

	@Override
	public Plugin plugin() {
		return new Plugin("OSS配置", "resources/templates/ossList.html");
	}

}
