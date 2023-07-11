package com.fluxsocket.traffic;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;

@Configuration
//@EnableWebSocketMessageBroker
@EnableWebSocket
public class TrafficAppConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
	registry
	    .addHandler(new TrafficSocketHandler(), "/lights")
	    .setAllowedOrigins("*");
	//"http://localhost:4200");
	// .addInterceptors( new HttpSessionHandshakeInterceptor())
	// .withSockJS();

    }

    // @Override
    // public void configureMessageBroker(MessageBrokerRegistry registry) {
    // 	registry.enableStompBrokerRelay("/queue/", "/topic/");
    // 	registry.setApplicationDestinationPrefixes("/app/");
    // }
    /*
    @Bean
    public WebSocketContainerFactoryBean createWebSocketContainer() {
        WebSocketContainerFactoryBean container = new WebSocketContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        return container;
    }
    */
    /*
    @Bean
    public WebSocketContainerFactoryBean createWebSocketContainer() {
        WebSocketContainerFactoryBean container = new WebSocketContainerFactoryBean();
        container.setMaxBinaryMessageBufferSize(1024000);
        return container;
    }
    */

}
