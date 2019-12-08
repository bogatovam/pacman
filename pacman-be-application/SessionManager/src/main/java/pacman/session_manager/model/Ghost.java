package pacman.session_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ghost extends GameObject {
    private Color color;

    enum Color {RED, YELLOW, BLUE, PINK};
}
