package pacman.game_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ghost extends GameObject {
    private Color color;

    @JsonIgnore
    /** Game State for navigating */
    private GameState gameState;
    @JsonIgnore
    /** STEP for speed */
    private static final double STEP = 1.0/10;

    public Ghost(Point coords, Point speed, Color color, GameState gameState) {
        super(coords, speed);
        this.color = color;
        this.gameState = gameState;
    }

    private void go() {
        if(getCoords().isCenter()) {
            int x = Point.DoubleToNearInt(getCoords().x);
            int y = Point.DoubleToNearInt(getCoords().y);
            if(gameState.isCrossroads(x, y)) {
                Pacman pacman = getNearPacman();
                Point newCoord = null;

                if(color.equals(Color.RED)) {
                    //RED Ghost
                    newCoord = pacman.getCoords();
                } else if(color.equals(Color.YELLOW)) {
                    //YELLOW Ghost
                    if(getCoords().getDistance(pacman.getCoords()) > 8) {
                        newCoord = pacman.getCoords();
                    } else
                        //This is DEFAULT COORDS for yellow ghost
                        newCoord = new Point(32.0, 0.0);
                } else if(color.equals(Color.BLUE)) {
                    //BLUE ghost
                    newCoord = pacman.getCoords();
                    Point pSpeed = pacman.getSpeed();
                    if(pSpeed.x > 0) {
                        newCoord.x += 2.0;
                    } else if(pSpeed.x < 0) {
                        newCoord.x -= 2.0;
                    }
                    if(pSpeed.y > 0) {
                        newCoord.y += 2.0;
                    } else if(pSpeed.y < 0) {
                        newCoord.y -= 2.0;
                    }
                    double deltaX = newCoord.x - getCoords().x;
                    double deltaY = newCoord.y - getCoords().y;
                    newCoord.x += deltaX;
                    newCoord.y += deltaY;
                } else if(color.equals(Color.PINK)) {
                    //PINK Ghost
                    newCoord = pacman.getCoords();
                    Point pSpeed = pacman.getSpeed();
                    if(pSpeed.x > 0) {
                        newCoord.x += 4.0;
                    } else if(pSpeed.x < 0) {
                        newCoord.x -= 4.0;
                    }
                    if(pSpeed.y > 0) {
                        newCoord.y += 4.0;
                    } else if(pSpeed.y < 0) {
                        newCoord.y -= 4.0;
                    }
                }
                setNewSpeed(newCoord);
            } else {
                //Just go to next coords
                Point coords = getCoords();
                coords.x += getSpeed().x * STEP;
                coords.y += getCoords().y * STEP;
            }
        } else {
            //Just go to next coords
            Point coords = getCoords();
            coords.x += getSpeed().x * STEP;
            coords.y += getCoords().y * STEP;
        }
    }

    private Pacman getNearPacman() {
        List<Pacman> pacmanList = gameState.getPacman();
        if(pacmanList.size() < 1) return null;
        Pacman nearPacman = pacmanList.get(0);
        double distance = getCoords().getDistance(nearPacman.getCoords());
        for(int i = 1; i < pacmanList.size(); i++) {
            Pacman curP = pacmanList.get(i);
            double curD = getCoords().getDistance(curP.getCoords());
            if(curD < distance) nearPacman = curP;
        }
        return nearPacman;
    }

    private void setNewSpeed(Point destination) {
        Point prevSpeed = getSpeed();
        //TODO setNewSpeed
    }

    enum Color {RED, YELLOW, BLUE, PINK};
}
