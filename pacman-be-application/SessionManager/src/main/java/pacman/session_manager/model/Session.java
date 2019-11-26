package pacman.session_manager.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Session {
    private String id = UUID.randomUUID().toString();
    private List<User> players = new ArrayList<>();
    private List<User> watchers = new ArrayList<>();

    public Session(List<User> players) {
        this.players = players;
    }

    public boolean containUser(String userId) {
        return players.stream().map(user -> user.getId()).anyMatch(id -> userId.equals(id)) ||
                watchers.stream().map(user -> user.getId()).anyMatch(id -> userId.equals(id));
    }
}
