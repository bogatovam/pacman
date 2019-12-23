package pacman.player_manager.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocketMessage {
    String sessionId;

    public Mono<String> toJSON() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String result = mapper.writeValueAsString(this);
            return Mono.just(result);
        } catch (JsonProcessingException e) {
            return Mono.error(e);
        }
    }
}
