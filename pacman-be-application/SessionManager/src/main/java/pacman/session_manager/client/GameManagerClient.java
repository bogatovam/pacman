package pacman.session_manager.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.reactivestreams.Publisher;
import pacman.session_manager.model.GameState;
import pacman.session_manager.model.User;
import reactor.core.publisher.Mono;

import java.util.List;

@Headers({"Accept: application/json"})
public interface GameManagerClient {
    @RequestLine("GET /game/{id}")
    Mono<GameState> getGameState(@Param("id") String id);

    @RequestLine("PUT /game")
    Mono<String> createGame(Publisher<List<User>> players);

    @RequestLine("DELETE /game/{id}")
    @Headers("Content-Type: application/json")
    Mono<GameState> closeGame(@Param("id") String id);
}
