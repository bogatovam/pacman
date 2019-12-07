package pacman.session_manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pacman.session_manager.client.GameManagerClient;
import reactivefeign.webclient.WebReactiveFeign;

@Configuration
public class ClientConfig {
    @Value("${pacman.game_manager.url}")
    private String gameManagerUrl;

    @Bean
    GameManagerClient gameManagerClient() {
        return WebReactiveFeign.<GameManagerClient>builder()
                .target(GameManagerClient.class, gameManagerUrl);
    }
}
