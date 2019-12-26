package pacman.gateway.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pacman.gateway.model.User;
import pacman.gateway.service.UserService;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.method;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Controller
public class UserHandler {

    @Autowired
    private UserService userService;

    private HandlerFunction<ServerResponse> getUser() {
        return request -> Mono.just(request.pathVariable("userId"))
                .flatMap(userId -> userService.getUser(userId))
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .syncBody(user))
                .switchIfEmpty(ServerResponse.notFound().build())
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    private HandlerFunction<ServerResponse> createUser() {
        return request -> request.bodyToMono(User.class)
                .flatMap(user -> userService.createUser(user))
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .syncBody(user))
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    public RouterFunction<ServerResponse> routes() {
        return nest(path("/user"),
                route(GET("/{userId}"), getUser())
                        .and(route(method(HttpMethod.PUT), createUser())));
    }
}
