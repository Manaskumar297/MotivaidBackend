package com.manas.motivaid.motivaid.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.corundumstudio.socketio.SocketIOServer;

@Configuration
public class SocketIOConfig {
	@Bean
	public SocketIOServer socketIOServer() {
		 com.corundumstudio.socketio.Configuration config =
	                new com.corundumstudio.socketio.Configuration();
		  config.setHostname("0.0.0.0");
	        config.setPort(9092);

	        return new SocketIOServer(config);
	}

}
