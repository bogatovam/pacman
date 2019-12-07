package pacman.player_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import pacman.player_manager.handler.ActionPlayerHandler;
import pacman.player_manager.handler.RegistPlayerHandler;

@Configuration
public class RoutesConfig {
    @Bean
    RouterFunction<ServerResponse> routes(RegistPlayerHandler registPlayerController,
                                          ActionPlayerHandler actionPlayerHandler) {
        return registPlayerController.routes().and(actionPlayerHandler.routes());
    }
}
