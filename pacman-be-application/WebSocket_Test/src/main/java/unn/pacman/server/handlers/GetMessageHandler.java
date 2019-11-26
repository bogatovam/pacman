package unn.pacman.server.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import unn.pacman.server.publisher.MessagePublisher;

@Component
public class GetMessageHandler implements WebSocketHandler {
    private MessagePublisher messagePublisher;
    private Flux<Boolean> values;

    @Autowired
    public GetMessageHandler(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
        this.values = Flux.create(messagePublisher).share();
    }

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        final Flux<WebSocketMessage> msg = values
                .map(wasPressed -> String.valueOf(wasPressed))
                .map(message -> webSocketSession.textMessage(message));

        return webSocketSession.send(msg);
    }

}
