package pacman.game_manager.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.util.UriComponentsBuilder;
import pacman.game_manager.model.GameStatus;
import pacman.game_manager.publisher.GamePublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ChangingGameHandler implements WebSocketHandler {

    private Flux<GameStatus> changingGames;

    @Autowired
    public ChangingGameHandler(GamePublisher gamePublisher) {
        changingGames = Flux.create(gamePublisher).share();
    }

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        Flux<WebSocketMessage> messages = changingGames
                .flatMap(sessionStatus -> sessionStatus.toJSON())
                .onErrorResume(e -> Mono.just(e.getMessage()))
                .map(s -> webSocketSession.textMessage(s));
        return webSocketSession.send(messages);
    }
}
