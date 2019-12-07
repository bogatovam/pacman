package pacman.game_manager.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStatus {
    private GameState gameState;
    private Status status;

    public enum Status {CHANGED, REMOVED}

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
