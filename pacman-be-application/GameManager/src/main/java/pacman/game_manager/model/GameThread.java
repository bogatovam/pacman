package pacman.game_manager.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.converter.json.GsonBuilderUtils;
import pacman.game_manager.publisher.GamePublisher;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameThread extends Thread {
    private GameState gameState;
    private GamePublisher gamePublisher;

    @Override
    public void run() {
        // TODO: продумать работу в отдельный потоках
    }
}
