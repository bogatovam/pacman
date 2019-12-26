package pacman.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pacman.gateway.handler.UserHandler;

@Configuration
public class RoutersConfig {

    @Bean
    RouterFunction<ServerResponse> routes(UserHandler userHandler) {
        return userHandler.routes();
    }
}
