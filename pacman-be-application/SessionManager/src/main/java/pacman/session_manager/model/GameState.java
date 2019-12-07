package pacman.session_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class GameState {
    private String id;
    private List<Pacman> pacmen;
    private List<Ghost> ghosts;
    private CellType[][] cellMatrix;
    private Integer level;
    private Long time;

    enum CellType {EMPTY, SCORE, WALL}
}
