package pacman.game_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Data
@ToString(exclude="gameState")
@NoArgsConstructor
@AllArgsConstructor
public class Ghost extends GameObject {
    @JsonIgnore
    private static final Logger LOG = LoggerFactory.getLogger(Pacman.class);

    private Color color;

    @JsonIgnore
    /** Default Point */
    private Point DefaultPoint;

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
        if(color.equals(Color.RED)) {
            DefaultPoint = new Point(-3.0, 28.0);
        } else if(color.equals(Color.BLUE)) {
            DefaultPoint = new Point(34.0, 28.0);
        } else if(color.equals(Color.PINK)) {
            DefaultPoint = new Point(-3.0, -1.0);
        } else DefaultPoint = new Point(34.0, -1.0);
     }

    public void go() {
        if(getCoords().isCenter()) {
            int x = Point.DoubleToNearInt(getCoords().x);
            int y = Point.DoubleToNearInt(getCoords().y);
            if(gameState.isWall(x, y)) {
                LOG.debug(
                        "WTF GHOST? " + x + " " + y);
            }
            if(gameState.isCrossroads(x, y)) {
                Pacman pacman = getNearPacman();
                Point newCoord = null;
                if(pacman == null) {
                    newCoord = new Point(DefaultPoint.x, DefaultPoint.y);
                } else if(color.equals(Color.RED)) {
                    //RED Ghost
                    newCoord = new Point(pacman.getCoords().x, pacman.getCoords().y);
                } else if(color.equals(Color.YELLOW)) {
                    //YELLOW Ghost
                    if(getCoords().getDistance(pacman.getCoords()) > 8) {
                        newCoord = new Point(pacman.getCoords().x, pacman.getCoords().y);
                    } else
                        newCoord = new Point(DefaultPoint.x, DefaultPoint.y);
                } else if(color.equals(Color.BLUE)) {
                    //BLUE ghost
                    Ghost ghost = gameState.getGhosts().get(0);
                    for (Ghost g:gameState.getGhosts()) {
                        if(g.getColor().equals(Color.RED)) {
                            ghost = g;
                            break;
                        }
                    }
                    Pacman redPacman = ghost.getNearPacman();
                    newCoord = new Point(redPacman.getCoords().x, redPacman.getCoords().y);
                    Point pSpeed = redPacman.getSpeed();
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
                    double deltaX = newCoord.x - ghost.getCoords().x;
                    double deltaY = newCoord.y - ghost.getCoords().y;
                    newCoord.x += deltaX;
                    newCoord.y += deltaY;
                } else if(color.equals(Color.PINK)) {
                    //PINK Ghost
                    newCoord = new Point(pacman.getCoords().x, pacman.getCoords().y);
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
                setNewSpeedInCrossroad(newCoord);
            } else {
                //Check rotate ghost and set new speed
                Point prevSpeed = new Point(getSpeed().x, getSpeed().y);
                boolean left = !gameState.isWall(x, y-1);
                boolean up = !gameState.isWall(x-1, y);
                boolean right = !gameState.isWall(x, y+1);
                boolean down = !gameState.isWall(x+1, y);
                if(prevSpeed.x == 0 && prevSpeed.y == 0) {
                    //Ghost don't moved
                    if(left) setSpeed(new Point(0.0, -1.0));
                    else if(up) setSpeed(new Point(-1.0, 0.0));
                    else if(right) setSpeed(new Point(0.0, 1.0));
                    else setSpeed(new Point(1.0, 0.0));
                } else if(prevSpeed.x > 0) {
                    double arg = Math.abs(prevSpeed.x);
                    //Ghost moved down can't go up
                    if(left) setSpeed(new Point(0.0, -arg));
                    else if(right) setSpeed(new Point(0.0, arg));
                    else if(down) setSpeed(new Point(arg, 0.0));
                    else setSpeed(new Point(-arg, 0.0));
                } else if(prevSpeed.x < 0) {
                    double arg = Math.abs(prevSpeed.x);
                    //Ghost moved up can't go down
                    if(left) setSpeed(new Point(0.0, -arg));
                    else if(up) setSpeed(new Point(-arg, 0.0));
                    else if(right) setSpeed(new Point(0.0, arg));
                    else setSpeed(new Point(arg, 0.0));
                } else if(prevSpeed.y > 0) {
                    double arg = Math.abs(prevSpeed.y);
                    //Ghost moved right can't go left
                    if(up) setSpeed(new Point(-arg, 0.0));
                    else if (right) setSpeed(new Point(0.0, arg));
                    else if(down) setSpeed(new Point(arg, 0.0));
                    else setSpeed(new Point(0.0, -arg));
                } else if(prevSpeed.y < 0) {
                    double arg = Math.abs(prevSpeed.y);
                    //Ghost moved left can't go right
                    if(left) setSpeed(new Point(0.0, -arg));
                    else if(up) setSpeed(new Point(-arg, 0.0));
                    else if(down) setSpeed(new Point(arg, 0.0));
                    else setSpeed(new Point(0.0, arg));
                }
            }
        }
        //Check rotate ghost and
        //just go to next coords
        double deltaX = getSpeed().x * STEP;
        double deltaY = getSpeed().y * STEP;
        getCoords().x += deltaX;
        getCoords().y += deltaY;
    }

    public Pacman getNearPacman() {
        List<Pacman> pacmanList = gameState.getPacman();
        if(pacmanList.size() < 1) return null;
        Pacman nearPacman = null;
        double distance = Double.MAX_VALUE;
        for(int i = 0; i < pacmanList.size(); i++) {
            Pacman curP = pacmanList.get(i);
            if(curP.isInvisible()) continue;
            double curD = getCoords().getDistance(curP.getCoords());
            if(curD < distance) {
                distance = curD;
                nearPacman = curP;
            }
        }
        return nearPacman;
    }

    //ONLY CROSSROAD
    private void setNewSpeedInCrossroad(Point destination) {
        Point prevSpeed = new Point(getSpeed().x, getSpeed().y);
        int x = Point.DoubleToNearInt(getCoords().x);
        int y = Point.DoubleToNearInt(getCoords().y);
        //Minimum 3 true
        boolean left = !gameState.isWall(x, y-1);
        boolean up = !gameState.isWall(x-1, y);
        boolean right = !gameState.isWall(x, y+1);
        boolean down = !gameState.isWall(x+1, y);
        double fromLeft = new Point(getCoords().x, getCoords().y - 1.0).getDistance(destination);
        double fromUp = new Point(getCoords().x - 1.0, getCoords().y).getDistance(destination);
        double fromRight = new Point(getCoords().x, getCoords().y + 1.0).getDistance(destination);
        double fromDown = new Point(getCoords().x + 1.0, getCoords().y).getDistance(destination);
        if(prevSpeed.x == 0 && prevSpeed.y == 0) {
            //Ghost don't moved
            if(left) setSpeed(new Point(0.0, -1.0));
            else if(up) setSpeed(new Point(-1.0, 0.0));
            else if(right) setSpeed(new Point(0.0, 1.0));
            else setSpeed(new Point(1.0, 0.0));
        } if(prevSpeed.x > 0) {
            double arg = Math.abs(prevSpeed.x);
            //Ghost moved down can't go up
            if(left && right && down) {
                //go left
                if(fromLeft <= fromDown && fromLeft <= fromRight) setSpeed(new Point(0.0, -arg));
                //go down
                else if(fromDown <= fromLeft && fromDown <= fromRight) setSpeed(new Point(arg, 0.0));
                //go right
                else setSpeed(new Point(0.0, arg));
            } else if(left && right) {
                if(fromLeft <= fromRight) setSpeed(new Point(0.0, -arg));
                else setSpeed(new Point(0.0, arg));
            } else if(left && down) {
                if(fromLeft <= fromDown) setSpeed(new Point(0.0, -arg));
                else setSpeed(new Point(arg, 0.0));
            } else {
                if(fromDown <= fromRight) setSpeed(new Point(arg, 0.0));
                else setSpeed(new Point(0.0, arg));
            }
        } else if(prevSpeed.x < 0) {
            //Ghost moved up can't go down
            double arg = Math.abs(prevSpeed.x);
            if(left && up && right) {
                //go left
                if(fromLeft <= fromRight && fromLeft <= fromUp) setSpeed(new Point(0.0, -arg));
                //go up
                else if(fromUp <= fromLeft && fromUp <= fromRight) setSpeed(new Point(-arg, 0.0));
                //go right
                else setSpeed(new Point(0.0, arg));
            } else if(left && up) {
                if(fromLeft <= fromUp) setSpeed(new Point(0.0, -arg));
                else setSpeed(new Point(-arg, 0.0));
            } else if(left && right) {
                if(fromLeft <= fromRight) setSpeed(new Point(0.0, -arg));
                else setSpeed(new Point(0.0, arg));
            } else {
                if(fromUp <= fromRight) setSpeed(new Point(-arg, 0.0));
                else setSpeed(new Point(0.0, arg));
            }
        } else if(prevSpeed.y > 0) {
            //Ghost moved right can't go left
            double arg = Math.abs(prevSpeed.y);
            if(up && right && down) {
                if(fromUp <= fromDown && fromUp <= fromRight) setSpeed(new Point(-arg, 0.0));
                else if(fromRight <= fromUp && fromRight <= fromRight) setSpeed(new Point(0.0, arg));
                else setSpeed(new Point(arg, 0.0));
            } else if(up && right) {
                if(fromUp <= fromRight) setSpeed(new Point(-arg, 0.0));
                else setSpeed(new Point(0.0, arg));
            } else if(up && down) {
                if(fromUp <= fromDown) setSpeed(new Point(-arg, 0.0));
                else setSpeed(new Point(arg, 0.0));
            } else {
                if(fromRight <= fromDown) setSpeed(new Point(0.0, arg));
                else setSpeed(new Point(arg, 0.0));
            }
        } else if(prevSpeed.y < 0) {
            //Ghost moved left can't go right
            double arg = Math.abs(prevSpeed.y);
            if(left && up && down) {
                if(fromLeft <= fromUp && fromLeft <= fromDown) setSpeed(new Point(0.0, -arg));
                else if(fromUp <= fromLeft && fromUp <= fromDown) setSpeed(new Point(-arg, 0.0));
                else setSpeed(new Point(arg, 0.0));
            } else if(left && up) {
                if(fromLeft <= fromUp) setSpeed(new Point(0.0, -arg));
                else setSpeed(new Point(-arg, 0.0));
            } else if(left && down) {
                if(fromLeft <= fromDown) setSpeed(new Point(0.0, -arg));
                else setSpeed(new Point(arg, 0.0));
            } else {
                if(fromUp <= fromDown) setSpeed(new Point(-arg, 0.0));
                else setSpeed(new Point(arg, 0.0));
            }
        }
    }

    enum Color {RED, YELLOW, BLUE, PINK};
}
