package pacman.session_manager.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import pacman.session_manager.model.SessionStatus;
import pacman.session_manager.publisher.ChangingSessionPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ChangingSessionHandler implements WebSocketHandler {

    private Flux<SessionStatus> changingSessions;

    @Autowired
    public ChangingSessionHandler(ChangingSessionPublisher changingSessionPublisher) {
        changingSessions = Flux.create(changingSessionPublisher).share();
    }

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        String sessionId = UriComponentsBuilder.fromUri(webSocketSession.getHandshakeInfo().getUri())
                .build()
                .getQueryParams()
                .get("sessionId")
                .get(0);
        Flux<WebSocketMessage> messages =changingSessions.filter(sessionStatus -> sessionStatus.getSession().getId().equals(sessionId))
                .flatMap(sessionStatus -> sessionStatus.toJSON())
                .onErrorResume(e -> Mono.just(e.getMessage()))
                .map(s -> webSocketSession.textMessage(s));
        return webSocketSession.send(messages);
    }
}
