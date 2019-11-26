package pacman.player_manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pacman.player_manager.client.SessionManagerClient;
import reactivefeign.webclient.WebReactiveFeign;

@Configuration
public class ClientConfig {
    @Value("${pacman.session_manager.url}")
    private String sessionManagerUrl;

    @Bean
    SessionManagerClient sessionManagerClient() {
        return WebReactiveFeign.<SessionManagerClient>builder()
                .target(SessionManagerClient.class, sessionManagerUrl);
    }

}
