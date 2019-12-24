package pacman.player_manager.client;

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

import java.io.IOException;
import java.net.URI;

@Component
@EnableAsync
public class InsideWebSocketClient {
    private static final Logger LOG = LoggerFactory.getLogger(InsideWebSocketClient.class);

    @Value("${pacman.player_manager.url}")
    private String playerManagerUrl;

    @Async
    @EventListener
    public void gameManagerWebSocketHandle(ContextRefreshedEvent event) {
        LOG.info("Create inside connection");
        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(URI.create(playerManagerUrl + "/wait"), session -> session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(message -> LOG.warn("Get inside message: " + message))
                .then()
                .log())
                .block();
    }
}
