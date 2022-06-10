package gd.mall.config;

import gd.mall.service.MessageService;
import gd.mall.ws.ChatEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Autowired
    private void setMessageService(MessageService messageService)
    {
        ChatEndpoint.messageService=messageService;
    }
}
