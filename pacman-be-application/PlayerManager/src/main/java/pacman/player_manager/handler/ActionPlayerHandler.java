package pacman.player_manager.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pacman.player_manager.client.GameManagerClient;
import pacman.player_manager.model.PlayerAction;
import pacman.player_manager.service.PlayerQueueService;
import reactor.core.publisher.Mono;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Controller
public class ActionPlayerHandler {

    @Autowired
    private GameManagerClient gameManagerClient;

    private HandlerFunction<ServerResponse> registAction() {
        return request -> request.bodyToMono(PlayerAction.class)
                .flatMap(playerAction -> gameManagerClient
                        .openSession(request.pathVariable("gameId"), Mono.just(playerAction)))
                .flatMap(result -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .syncBody(result))
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    public RouterFunction<ServerResponse> routes() {
        return route(POST("/action/{gameId}"), registAction());
    }
}
