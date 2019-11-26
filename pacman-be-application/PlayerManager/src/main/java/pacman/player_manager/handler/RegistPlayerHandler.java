package pacman.player_manager.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pacman.player_manager.service.PlayerQueueService;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Controller
public class RegistPlayerHandler {

    @Autowired
    private PlayerQueueService playerQueueService;

    private HandlerFunction<ServerResponse> registUser() {
        return request -> Mono.just(request.pathVariable("userId"))
                .flatMap(userId -> playerQueueService.addIntoQueue(userId))
                .flatMap(playerId -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .syncBody(playerId))
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    private HandlerFunction<ServerResponse> removeUser() {
        return request -> Mono.just(request.pathVariable("userId"))
                .flatMap(userId -> playerQueueService.removeFromQueue(userId))
                .flatMap(playerId -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .syncBody(playerId))
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    public RouterFunction<ServerResponse> routes() {
        return nest(path("/queue"),
        route(GET("/regist/{userId}"), registUser())
                .and(route(GET("/remove/{userId}"), removeUser())));
    }
}
