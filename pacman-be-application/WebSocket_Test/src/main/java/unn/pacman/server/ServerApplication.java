package unn.pacman.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import unn.pacman.server.handlers.SetMessageHandler;

@SpringBootApplication
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    @Bean
    RouterFunction<ServerResponse> routes(SetMessageHandler setMessageHandler) {
        return setMessageHandler.setValue();
    }
}
