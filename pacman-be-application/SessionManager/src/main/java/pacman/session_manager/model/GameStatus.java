package pacman.session_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStatus {
    private GameState gameState;
    private Status status;

    public enum Status {CHANGED, REMOVED}
}
