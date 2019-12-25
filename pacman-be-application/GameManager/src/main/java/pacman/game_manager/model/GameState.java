package pacman.game_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameState {
    private String id = UUID.randomUUID().toString();
    private List<Pacman> pacman = new ArrayList<>();
    private List<Ghost> ghosts = new ArrayList<>();
    private CellType[][] cellMatrix;
    private Integer level = 1;
    private Long time = 0L;

    /** Const field - Game Width */
    @JsonIgnore
    private static final int GAME_WIDTH = 28;
    /** Const field - Game Height */
    @JsonIgnore
    private static final int GAME_HEIGHT = 31;

    /** Const field - Point by one Score */
    @JsonIgnore
    private static final int POINT_BY_SCORE = 100;

    @JsonIgnore
    private Date startTime = new Date();

    enum CellType {EMPTY, SCORE, WALL}

    public GameState(List<User> players) throws Exception {

        if(players.size() == 0) {
            players.add(null);
            players.add(null);
            players.add(null);
            players.add(null);
        }
        if (players.size() > 4)
            throw new Exception("A lot of players for game");

        /** Pacman's coords */
        if(players.size() >= 1) {
            Point FirstPacmanCoodrs = new Point(1.0, 1.0);
            Point FirstPacmanSpeed = new Point(0.0, 0.0);
            pacman.add(new Pacman(FirstPacmanCoodrs, FirstPacmanSpeed, Pacman.Color.RED, players.get(0), this));

            if(players.size() >= 2) {
                Point SecondPacmanCoodrs = new Point(1.0, 26.0);
                Point SecondPacmanSpeed = new Point(0.0, 0.0);
                pacman.add(new Pacman(SecondPacmanCoodrs, SecondPacmanSpeed, Pacman.Color.YELLOW, players.get(1), this));

                if(players.size() >= 3) {
                    Point ThirdPacmanCoodrs = new Point(29.0, 1.0);
                    Point ThirdPacmanSpeed = new Point(0.0, 0.0);
                    pacman.add(new Pacman(ThirdPacmanCoodrs, ThirdPacmanSpeed, Pacman.Color.BLUE, players.get(2), this));
                }

                if(players.size() == 4) {
                    Point FourthPacmanCoodrs = new Point(29.0, 26.0);
                    Point FourthPacmanSpeed = new Point(0.0, 0.0);
                    pacman.add(new Pacman(FourthPacmanCoodrs, FourthPacmanSpeed, Pacman.Color.PINK, players.get(3), this));
                }
            }
        }

        /** Ghost's coords */
        Point RedGhostCoodrs = new Point(11.0, 9.0);
        Point RedGhostSpeed = new Point(0.0, 0.0);
        ghosts.add(new Ghost(RedGhostCoodrs, RedGhostSpeed, Ghost.Color.RED, this));

        Point BlueGhostCoords = new Point(11.0, 18.0);
        Point BlueGhostSpeed = new Point(0.0, 0.0);
        ghosts.add(new Ghost(BlueGhostCoords, BlueGhostSpeed, Ghost.Color.BLUE, this));

        Point PinkGhostCoords = new Point(17.0, 9.0);
        Point PinkGhostSpeed = new Point(0.0, 0.0);
        ghosts.add(new Ghost(PinkGhostCoords, PinkGhostSpeed, Ghost.Color.PINK, this));

        Point YellowGhostCoords = new Point(17.0, 18.0);
        Point YellowGhostSpeed = new Point(0.0, 0.0);
        ghosts.add(new Ghost(YellowGhostCoords, YellowGhostSpeed, Ghost.Color.YELLOW, this));

        //Generate Standard Cell Matrix
        cellMatrix = createStandardCellMatrix();
    }

    /** Generate Standard Cell Matrix By Standard template */
    private static CellType[][] createStandardCellMatrix() {
        CellType[][] cellMatrix = new CellType[GAME_HEIGHT][];

        String[] templates = new String[GAME_HEIGHT];
        templates[0]  = "WWWWWWWWWWWWWWWWWWWWWWWWWWWW";
        templates[1]  = "WE***********WW***********EW";
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
        templates[29] = "WE************************EW";
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

    public boolean isCrossroads(int x, int y) {
        if (x < 0 || x > 30 || y < 0 || y > 27) return false;
        if(cellMatrix[x][y] == CellType.WALL) return false;
        int numDest = 0;
        if(!isWall(x-1, y)) numDest++;
        if(!isWall(x+1, y)) numDest++;
        if(!isWall(x, y-1)) numDest++;
        if(!isWall(x, y+1)) numDest++;
        return numDest >= 3;
    }

    public boolean isWall(int x, int y) {
        if (x < 0 || x > 30 || y < 0 || y > 27) return true;
        return cellMatrix[x][y] == CellType.WALL;
    }

    public int pickUpScore(int x, int y) {
        if (x < 0 || x > 30 || y < 0 || y > 27) return 0;
        else if(getCellMatrix()[x][y] == CellType.SCORE) {
            getCellMatrix()[x][y] = CellType.EMPTY;
            return POINT_BY_SCORE;
        } else return 0;
    }

    public boolean isGhost(int x, int y) {
        for (Ghost g: ghosts) {
            int gX = Point.DoubleToNearInt(g.getCoords().x);
            int gY = Point.DoubleToNearInt(g.getCoords().y);
            if (gX == x && gY == y) return true;
        }
        return false;
    }

    public void update() {
        time = new Date().getTime() - startTime.getTime();
        //All Packman go
        for (Pacman p: pacman)
            p.go();
        //All Ghost go
        for(Ghost g: ghosts)
            g.go();
    }

    public boolean canPacmanGoTo(Pacman p, int x, int y) {
        if(isWall(x, y)) return false;
        else {
            for (Pacman pacman: pacman) {
                if(p!=pacman) {
                    double speedX = pacman.getSpeed().x;
                    double speedY = pacman.getSpeed().y;
                    int nextX = 0, nextY = 0;
                    if(speedX > 0) nextX = 1;
                    if(speedX < 0) nextX = -1;
                    if(speedY > 0) nextX = 1;
                    if(speedY < 0) nextY = -1;
                    if(pacman.getCoords().x + nextX == x && pacman.getCoords().y + nextY == y) return false;
                }
            }
            return true;
        }
    }

    public void update(PlayerAction playerAction) {

        Pacman pacman = this.pacman.stream()
                .filter(player -> player.getUser().getId().equals(playerAction.getPlayerId()))
                .findFirst().orElse(null);

        if (pacman != null) {
            pacman.updateSpeed(playerAction.getSpeedVector());
        }
    }

    /** TEST MAIN*/
    public static void main(String[] a) throws Exception {
        GameState state = new GameState(new ArrayList<>());
        int step = 0;
        while(true) {
            /** Out for Ghost */
            if(step % 10 == 0) {
                System.out.println("RED Coords: " + state.getGhosts().get(0).getCoords().x.floatValue() + " " + state.getGhosts().get(0).getCoords().y.floatValue());
                System.out.println("BLUE Coords: " + state.getGhosts().get(1).getCoords().x.floatValue() + " " + state.getGhosts().get(1).getCoords().y.floatValue());
                System.out.println("PINK Coords: " + state.getGhosts().get(2).getCoords().x.floatValue() + " " + state.getGhosts().get(2).getCoords().y.floatValue());
                System.out.println("YELLOW Coords: " + state.getGhosts().get(3).getCoords().x.floatValue() + " " + state.getGhosts().get(3).getCoords().y.floatValue());
                System.out.println("---");
                step = 0;
            }
            step++;
            state.update();
            try {
                Thread.sleep(100);
            } catch (InterruptedException  e) {
                break;
            }
        }
    }
}
