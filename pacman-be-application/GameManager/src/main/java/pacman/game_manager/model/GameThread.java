package pacman.game_manager.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.json.GsonBuilderUtils;
import pacman.game_manager.publisher.GamePublisher;

@Data
@NoArgsConstructor
public class GameThread extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(GameThread.class);
    private GameState gameState;
    private GamePublisher gamePublisher;

    @JsonIgnore
    private static int STEP_TIME = 150;

    public GameThread(String name, GameState gameState, GamePublisher gamePublisher) {
        super(name);
        this.gameState=gameState;
        this.gamePublisher=gamePublisher;
    }

    @Override
    public void run() {
        int step = 0;
        //Main game Thread
        while(!isInterrupted()) {
            try {
                //Update gameState
                gameState.update();
                //Out to console
                if(step % 10 == 0 ) {
                    LOG.debug("Update 1 cell");
                    step = 1;
                } else {
                    ++step;
                }
                //Push to publisher
                gamePublisher.push(new GameStatus(gameState, GameStatus.Status.CHANGED));
                //Thread sleep
                sleep(STEP_TIME);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}
