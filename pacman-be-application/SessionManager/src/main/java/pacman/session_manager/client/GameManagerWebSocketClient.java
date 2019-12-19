package pacman.session_manager.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import pacman.session_manager.model.GameStatus;
import pacman.session_manager.service.SessionService;
import reactor.core.publisher.SynchronousSink;

import java.io.IOException;
import java.net.URI;

@Component
@EnableAsync
public class GameManagerWebSocketClient {
    private static final Logger LOG = LoggerFactory.getLogger(GameManagerWebSocketClient.class);

    @Autowired
    SessionService sessionService;

    @Value("${pacman.game_manager.url}")
    private String gameManagerUrl;

    @Async
    @EventListener
    public void gameManagerWebSocketHandle(ContextRefreshedEvent event) {
        LOG.info("Create connection to GameManager");
        ObjectMapper objectMapper = new ObjectMapper();
        WebSocketClient client = new ReactorNettyWebSocketClient();
        client.execute(URI.create(gameManagerUrl + "/change"), session -> session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .handle((String message, SynchronousSink<GameStatus> sink) -> {
                    try {
                        sink.next(objectMapper.readValue(message, GameStatus.class));
                    } catch (IOException e) {
                        LOG.warn("Can't parse message from GameManager: " + message);
                    }
                })
                .doOnNext(gameStatus -> System.out.println("Get message from GameManager: " + gameStatus))
                .doOnNext(gameStatus -> sessionService.updateGameState(gameStatus))
                .then()
                .log())
                .block();
    }
}