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

    /** Const field - Game Width */
    @JsonIgnore
    private static final int GAME_WIDTH = 28;
    /** Const field - Game Height */
    @JsonIgnore
    private static final int GAME_HEIGHT = 31;

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
        //Generate Standard Cell Matrix
        cellMatrix = createStandardCellMatrix();
    }

    /** Generate Standard Cell Matrix By Standard template */
    private static CellType[][] createStandardCellMatrix() {
        CellType[][] cellMatrix = new CellType[GAME_HEIGHT][];

        String[] templates = new String[GAME_HEIGHT];
        templates[0]  = "WWWWWWWWWWWWWWWWWWWWWWWWWWWW";
        templates[1]  = "W************WW************W";
        templates[2]  = "W*WWWW*WWWWW*WW*WWWWW*WWWW*W";
        templates[3]  = "W*WWWW*WWWWW*WW*WWWWW*WWWW*W";
        templates[4]  = "W*WWWW*WWWWW*WW*WWWWW*WWWW*W";
        templates[5]  = "W**************************W";
        templates[6]  = "W*WWWW*WW*WWWWWWWW*WW*WWWW*W";
        templates[7]  = "W*WWWW*WW*WWWWWWWW*WW*WWWW*W";
        templates[8]  = "W******WW****WW****WW******W";
        templates[9]  = "WWWWWW*WWWWWEWWEWWWWW*WWWWWW";
        templates[10] = "EEEEEW*WWWWWEWWEWWWWW*WEEEEE";
        templates[11] = "EEEEEW*WWEEEEEEEEEEWW*WEEEEE";
        templates[12] = "EEEEEW*WWEPPPPPPPPEWW*WEEEEE";
        templates[13] = "EEEEEW*WWEPEEEEEEPEWW*WEEEEE";
        templates[14] = "EEEEEW*EEEPEEEEEEPEEE*WEEEEE";
        templates[15] = "EEEEEW*WWEPEEEEEEPEWW*WEEEEE";
        templates[16] = "EEEEEW*WWEPPPPPPPPEWW*WEEEEE";
        templates[17] = "EEEEEW*WWEEEEEEEEEEWW*WEEEEE";
        templates[18] = "EEEEEW*WW WWWWWWWW WW*WEEEEE";
        templates[19] = "WWWWWW*WW WWWWWWWW WW*WWWWWW";
        templates[20] = "W************WW************W";
        templates[21] = "W*WWWW*WWWWW*WW*WWWWW*WWWW*W";
        templates[22] = "W*WWWW*WWWWW*WW*WWWWW*WWWW*W";
        templates[23] = "W***WW****************WW***W";
        templates[24] = "WWW*WW*WW*WWWWWWWW*WW*WW*WWW";
        templates[25] = "WWW*WW*WW*WWWWWWWW*WW*WW*WWW";
        templates[26] = "W******WW****WW****WW******W";
        templates[27] = "W*WWWWWWWWWW*WW*WWWWWWWWWW*W";
        templates[28] = "W*WWWWWWWWWW*WW*WWWWWWWWWW*W";
        templates[29] = "W**************************W";
        templates[30] = "WWWWWWWWWWWWWWWWWWWWWWWWWWWW";

        for(int i = 0; i < GAME_HEIGHT; i++) {
            cellMatrix[i] = createRowByTemplate(templates[i].toCharArray());
        }

        return cellMatrix;
    }

    private static CellType[] createRowByTemplate(char[] template) {
        CellType[] cellRow = new CellType[template.length];
        for(int i = 0; i < template.length; i++) {
            if(template[i] == 'W' || template[i] == 'P') cellRow[i] = CellType.WALL;
            else if(template[i] == '*') cellRow[i] = CellType.SCORE;
            else cellRow[i] = CellType.EMPTY;
        }
        return cellRow;
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
