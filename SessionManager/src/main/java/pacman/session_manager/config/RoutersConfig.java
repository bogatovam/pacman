package pacman.session_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pacman.session_manager.handler.SessionHandler;

@Configuration
public class RoutersConfig {

    @Bean
    RouterFunction<ServerResponse> routes(SessionHandler sessionHandler) {
        return sessionHandler.routes();
    }
}
