package pacman.game_manager.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pacman.game_manager.model.PlayerAction;
import pacman.game_manager.model.User;
import pacman.game_manager.service.GameService;
import reactor.core.publisher.Mono;

import static io.netty.handler.codec.http.HttpMethod.DELETE;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Controller
public class GameStateHandler {

    @Autowired
    private GameService gameService;

    private HandlerFunction<ServerResponse>getGame() {
        return request -> Mono.just(request.pathVariable("id"))
                .flatMap(id -> gameService.getGame(id))
                .flatMap(gameState -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .syncBody(gameState))
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    private HandlerFunction<ServerResponse>createGame() {
        return request -> request.bodyToFlux(User.class)
                .collectList()
                .flatMap(playerList -> gameService.createGame(playerList))
                .flatMap(gameState -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .syncBody(gameState))
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    private HandlerFunction<ServerResponse>closeGame() {
        return request -> Mono.just(request.pathVariable("id"))
                .flatMap(id -> gameService.closeGame(id))
                .flatMap(gameState -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .syncBody(gameState))
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    private HandlerFunction<ServerResponse> registPlayerAction() {
        return request -> request.bodyToMono(PlayerAction.class)
                .doOnNext(playerAction -> gameService.playerEvent(request.pathVariable("id"), playerAction))
                .flatMap(playerAction -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .syncBody("OK"))
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    public RouterFunction<ServerResponse> routes() {
        return nest(path("/game"),
                route(POST("/{id}"), registPlayerAction())
                .and(route(GET("/{id}"), getGame()))
                .and(route(DELETE("/{id}"), closeGame()))
                .and(route(method(HttpMethod.PUT), createGame())));
    }
}
