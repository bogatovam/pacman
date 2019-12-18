package pacman.game_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    private Point newSpeed;

    @JsonIgnore
    /** STEP for speed */
    private static final double STEP = 1.0/10;
    @JsonIgnore
    /** Game State for navigating */
    private GameState gameState;

    public Pacman(Point score, Point speed, Color color, User user, GameState gameState) {
        super(score, speed);
        this.color = color;
        this.user = user;
        newSpeed = new Point(getSpeed().x, getSpeed().y);
        this.gameState = gameState;
    }

    public void go() {
        if(getCoords().isCenter()) {
            int x = Point.DoubleToNearInt(getCoords().x);
            int y = Point.DoubleToNearInt(getCoords().y);
            setSpeed(new Point(0.0, 0.0));
            if(newSpeed.x == 0 && newSpeed.y == 0) {
                //Pacman don't move
                return;
            } else if(newSpeed.x > 0) {
                //Pacman go down
                if(gameState.canPacmanGoTo(this, x+1, y)) {
                    setSpeed(new Point(newSpeed.x, newSpeed.y));
                } else return;
            } else if(newSpeed.x < 0) {
                //Pacman go up
                if(gameState.canPacmanGoTo(this, x-1, y)) {
                    setSpeed(new Point(newSpeed.x, newSpeed.y));
                } else return;
            } else if(newSpeed.y > 0) {
                //Pacman go right
                if(gameState.canPacmanGoTo(this, x, y+1)) {
                    setSpeed(new Point(newSpeed.x, newSpeed.y));
                } else return;
            } else if(newSpeed.y < 0) {
                //Pacman go left
                if(gameState.canPacmanGoTo(this, x, y-1)) {
                    setSpeed(new Point(newSpeed.x, newSpeed.y));
                } else return;
            }
        } else {
            //Just go next
            double deltaX = getSpeed().x * STEP;
            double deltaY = getSpeed().y * STEP;
            getCoords().x += deltaX;
            getCoords().y += deltaY;
        }
        score+= gameState.pickUpScore(Point.DoubleToNearInt(getCoords().x), Point.DoubleToNearInt(getCoords().y));
        if(gameState.isGhost(Point.DoubleToNearInt(getCoords().x), Point.DoubleToNearInt(getCoords().y))) lifeCount--;
        if(lifeCount > 0) {
            //TODO setNewCoords
        } else {
            //TODO DEATH
        }
    }

    public void updateSpeed(Point newSpeed) {
        this.newSpeed = newSpeed;
        if(newSpeed.x != 0 && getSpeed().x != 0) getSpeed().x = newSpeed.x;
        else if(newSpeed.y != 0 && getSpeed().y != 0) getSpeed().y = newSpeed.y;
    }

    enum Color {RED, YELLOW, BLUE, PINK}
}
