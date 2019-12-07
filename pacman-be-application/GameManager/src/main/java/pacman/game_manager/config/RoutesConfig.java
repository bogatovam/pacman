package pacman.game_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pacman.game_manager.handler.GameStateHandler;

@Configuration
public class RoutesConfig {
    @Bean
    RouterFunction<ServerResponse> routes(GameStateHandler gameStateHandler) {
        return gameStateHandler.routes();
    }
}
