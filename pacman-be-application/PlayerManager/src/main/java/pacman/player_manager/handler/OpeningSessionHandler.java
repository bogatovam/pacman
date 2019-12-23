package pacman.player_manager.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import pacman.player_manager.model.Session;
import pacman.player_manager.model.SocketMessage;
import pacman.player_manager.model.User;
import pacman.player_manager.publisher.OpeningSessionPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@Controller
public class OpeningSessionHandler implements WebSocketHandler {
    private Flux<Session> sessionFlux;

    @Autowired
    public OpeningSessionHandler(OpeningSessionPublisher sessionPublisher) {
        sessionFlux = Flux.create(sessionPublisher).share();
    }

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        String userId = UriComponentsBuilder.fromUri(webSocketSession.getHandshakeInfo().getUri())
                .build()
                .getQueryParams()
                .get("userId")
                .get(0);
        System.out.println(userId);
        Flux<WebSocketMessage> messages = sessionFlux
                .filter(session -> userId != null && session.getPlayers().stream()
                        .map(User::getId).collect(Collectors.toList()).contains(userId))
                .flatMap(session -> SocketMessage.builder().sessionId(session.getId()).build().toJSON())
                .map(webSocketSession::textMessage);
        return webSocketSession.send(messages);
    }
}
