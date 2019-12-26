package pacman.player_manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pacman.player_manager.client.GameManagerClient;
import pacman.player_manager.client.GatewayClient;
import pacman.player_manager.client.SessionManagerClient;
import reactivefeign.webclient.WebReactiveFeign;

@Configuration
public class ClientConfig {
    @Value("${pacman.session_manager.url}")
    private String sessionManagerUrl;

    @Value("${pacman.game_manager.url}")
    private String gameManagerUrl;

    @Value("${pacman.gateway.url}")
    private String gatewayUrl;

    @Bean
    SessionManagerClient sessionManagerClient() {
        return WebReactiveFeign.<SessionManagerClient>builder()
                .target(SessionManagerClient.class, sessionManagerUrl);
    }

    @Bean
    GameManagerClient gameManagerClient() {
        return WebReactiveFeign.<GameManagerClient>builder()
                .target(GameManagerClient.class, gameManagerUrl);
    }

    @Bean
    GatewayClient gatewayClient() {
        return WebReactiveFeign.<GatewayClient>builder()
                .target(GatewayClient.class, gatewayUrl);
    }

}
