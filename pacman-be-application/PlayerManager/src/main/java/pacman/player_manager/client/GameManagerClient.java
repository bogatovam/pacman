package pacman.player_manager.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.reactivestreams.Publisher;
import pacman.player_manager.model.PlayerAction;
import pacman.player_manager.model.Session;
import pacman.player_manager.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Headers({"Accept: application/json"})
public interface GameManagerClient {
    @RequestLine("POST /game/{id}")
    Mono<String> openSession(@Param("id") String gameId, Publisher<PlayerAction> playerAction);
}
