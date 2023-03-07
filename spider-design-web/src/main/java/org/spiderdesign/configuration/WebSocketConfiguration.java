package org.spiderdesign.configuration;

import org.spiderdesign.core.Spider;
import org.spiderdesign.websocket.WebSocketEditorServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 配置WebSocket
 * @author Administrator
 *
 */
@Configuration
public class WebSocketConfiguration {

	@Bean
	public ServerEndpointExporter endpointExporter(){
		return new ServerEndpointExporter();
	}

	@Autowired
	public void setSpider(Spider spider) {
		WebSocketEditorServer.spider = spider;
	}

}
