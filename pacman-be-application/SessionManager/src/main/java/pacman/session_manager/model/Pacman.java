package pacman.session_manager.model;

import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pacman extends GameObject {
    private Integer lifeCount = 3;
    private Integer score = 0;
    private Color color;
    private User user;

    public Pacman(Point score, Point speed, Color color, User user) {
        super(score, speed);
        this.color = color;
        this.user = user;
    }

    enum Color {RED, YELLOW, BLUE, PINK}
}
