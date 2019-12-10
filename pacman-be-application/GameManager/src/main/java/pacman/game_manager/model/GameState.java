package pacman.game_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameState {
    private String id = UUID.randomUUID().toString();
    private List<Pacman> pacman;
    private List<Ghost> ghosts;
    private CellType[][] cellMatrix;
    private Integer level = 1;
    private Long time = 0L;

    @JsonIgnore
    private Date startTime = new Date();

    enum CellType {EMPTY, SCORE, WALL}

    public GameState(List<User> players) throws Exception {
        pacman = new ArrayList<>();
        if (players.size() > 4)
            throw new Exception("A lot of players for game");
        for (int i = 0; i < players.size(); ++i) {
            // TODO: пределить начальное расположение пакманов и скорость
            Point coodrs = new Point(0.0, 0.0);
            Point speed = new Point(0.0, 0.0);
            pacman.add(new Pacman(coodrs, speed, Pacman.Color.values()[i], players.get(i)));
        }
        ghosts = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            // TODO: пределить начальное расположение призраков и скорость
            Point coodrs = new Point(0.0, 0.0);
            Point speed = new Point(0.0, 0.0);
            ghosts.add(new Ghost(coodrs, speed, Ghost.Color.values()[i]));
        }
        // TODO: пределить поле
        cellMatrix = new CellType[100][100];
    }

    public void update() {
        time = new Date().getTime() - startTime.getTime();
        //TODO: обновление
    }

    public void update(PlayerAction playerAction) {
        Pacman pacman = this.pacman.stream()
                .filter(player -> player.getUser().getId().equals(playerAction.getPlayerId()))
                .findFirst().orElse(null);
        update();
        if (pacman != null) {
            // TODO: определить длину вектора скорости
            pacman.setSpeed(playerAction.getSpeedVector());
        }
    }
}
