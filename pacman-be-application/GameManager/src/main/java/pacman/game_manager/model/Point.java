package pacman.game_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Point {
    public Double x;
    public Double y;

    public double getDistance(Point p) {
        return Math.sqrt((p.x - x)*(p.x - x) + (p.y - y)*(p.y - y));
    }

    /** Return true if x and y is Int */
    public boolean isCenter() {
        if (Math.abs(x - DoubleToNearInt(x)) <= 0.001 &&
                Math.abs(y - DoubleToNearInt(y)) <= 0.001) {
            x = (double) DoubleToNearInt(x);
            y = (double) DoubleToNearInt(y);
            return true;
        }
        return false;
    }

    /** Rounding to the nearest */
    public static int DoubleToNearInt(double d) {
        if(d - (int)d >= 0.5) return (int)d + 1;
        else return (int)d;
    }

}
