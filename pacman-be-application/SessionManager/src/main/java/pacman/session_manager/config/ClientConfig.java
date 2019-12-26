package pacman.session_manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pacman.session_manager.client.GameManagerClient;
import pacman.session_manager.client.GatewayClient;
import reactivefeign.webclient.WebReactiveFeign;

@Configuration
public class ClientConfig {
    @Value("${pacman.game_manager.url}")
    private String gameManagerUrl;

    @Value("${pacman.gateway.url}")
    private String gatewayUrl;

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
