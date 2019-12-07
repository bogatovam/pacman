package pacman.game_manager.model;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ghost extends GameObject {
    private Color color;

    public Ghost(Point coords, Point speed, Color color) {
        super(coords, speed);
        this.color = color;
    }

    enum Color {RED, YELLOW, BLUE, PINK};
}
