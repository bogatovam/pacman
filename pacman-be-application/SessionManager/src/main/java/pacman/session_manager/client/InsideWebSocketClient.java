package pacman.session_manager.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;

@Component
@EnableAsync
public class InsideWebSocketClient {
    private static final Logger LOG = LoggerFactory.getLogger(InsideWebSocketClient.class);

    @Value("${pacman.session_manager.url}")
    private String sessionManagerUrl;

    @Async
    @EventListener
    public void gameManagerWebSocketHandle(ContextRefreshedEvent event) {
        LOG.info("Create inside connection");
        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(URI.create(sessionManagerUrl + "/change"), session -> session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(message -> LOG.warn("Get inside message: " + message))
                .then()
                .log())
                .block();
    }
}
