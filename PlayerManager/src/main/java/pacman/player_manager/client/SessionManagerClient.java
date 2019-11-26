package pacman.player_manager.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.reactivestreams.Publisher;
import pacman.player_manager.model.Session;
import pacman.player_manager.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Headers({"Accept: application/json"})
public interface SessionManagerClient {

    @RequestLine("GET /session")
    Flux<Session> getOpenSessions();

    @RequestLine("GET /session/{sessionId}")
    Mono<Session> getSessionById(@Param("sessionId") String sessionId);

    @RequestLine("PUT /session")
    Mono<String> openSession(Publisher<List<User>> players);

    @RequestLine("GET /session/{sessionId}/close")
    @Headers("Content-Type: application/json")
    Mono<Session> closeSession(@Param("sessionId") String sessionId);
}
