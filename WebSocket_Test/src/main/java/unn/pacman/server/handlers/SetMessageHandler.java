package unn.pacman.server.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import unn.pacman.server.publisher.MessagePublisher;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Controller
public class SetMessageHandler {

    @Autowired
    private MessagePublisher messagePublisher;

    private HandlerFunction<ServerResponse> addValue() {
        return request -> request.bodyToMono(Boolean.class)
                .doOnNext(val -> System.out.println(val))
                .doOnNext(value -> messagePublisher.push(value))
                .map(val -> "OK")
                .flatMap(str -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .syncBody(str));
    }

    public RouterFunction<ServerResponse> setValue() {
        return route(POST("/setMessage"), addValue());
    }
}
