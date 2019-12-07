package pacman.session_manager.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pacman.session_manager.model.Session;
import pacman.session_manager.model.User;
import pacman.session_manager.service.SessionService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Controller
public class SessionHandler {

    @Autowired
    private SessionService sessionService;

    private HandlerFunction<ServerResponse> getOpenedSessions() {
        return request ->  ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(sessionService.getAllSessions(), Session.class)
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    private HandlerFunction<ServerResponse> getSessionById() {
        return request -> Mono.just(request.pathVariable("sessionId"))
                .flatMap(id -> sessionService.getSessionById(id))
                .flatMap(session -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .syncBody(session))
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    private HandlerFunction<ServerResponse> openSession() {
        return request -> request.bodyToFlux(User.class)
                .collectList()
                .flatMap(players -> sessionService.openSession(players))
                .flatMap(session -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .syncBody(session))
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    private HandlerFunction<ServerResponse> closeSession() {
        return request -> Mono.just(request.pathVariable("sessionId"))
                .flatMap(id -> sessionService.closeSession(id))
                .flatMap(session -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .syncBody(session))
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    private HandlerFunction<ServerResponse> addWatcher() {
        return request -> Mono.just(request.pathVariable("sessionId"))
                .flatMap(sessionId -> Mono.just(request.pathVariable("userId"))
                    .flatMap(userId -> sessionService.addWatcher(sessionId, userId)))
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .syncBody(user))
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    private HandlerFunction<ServerResponse> removeWatcher() {
        return request -> Mono.just(request.pathVariable("sessionId"))
                .flatMap(sessionId -> Mono.just(request.pathVariable("userId"))
                        .flatMap(userId -> sessionService.removeWatcher(sessionId, userId)))
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .syncBody(user))
                .onErrorResume(e -> ServerResponse.badRequest().syncBody(e.getMessage()));
    }

    public RouterFunction<ServerResponse> routes() {
        return nest(path("/session"),
                route(GET("/{sessionId}/user/{userId}"), addWatcher())
                        .and(route(DELETE("/{sessionId}/user/{userId}"), removeWatcher()))
                        .and(route(DELETE("/{sessionId}"), closeSession()))
                        .and(route(GET("/{sessionId}"), getSessionById()))
                        .and(route(method(HttpMethod.GET), getOpenedSessions()))
                        .and(route(method(HttpMethod.PUT), openSession())));
    }
}
