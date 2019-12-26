package pacman.player_manager.client;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import pacman.player_manager.model.User;
import reactor.core.publisher.Mono;

@Headers({"Accept: application/json"})
public interface GatewayClient {

    @RequestLine("GET /user/{userId}")
    Mono<User> getUser(@Param("userId") String userId);
}
