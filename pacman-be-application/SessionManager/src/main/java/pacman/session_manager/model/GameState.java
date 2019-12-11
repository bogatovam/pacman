package pacman.session_manager.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GameState {
    private String id;
    private List<Pacman> pacman;
    private List<Ghost> ghosts;
    private CellType[][] cellMatrix;
    private Integer level;
    private Long time;

    enum CellType {EMPTY, SCORE, WALL}
}
