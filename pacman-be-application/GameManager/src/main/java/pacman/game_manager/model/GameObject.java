package pacman.game_manager.model;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameObject {
    private Point coords;
    private Point speed;
}
