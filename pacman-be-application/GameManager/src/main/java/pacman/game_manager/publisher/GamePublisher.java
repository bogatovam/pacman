package pacman.game_manager.publisher;

import org.springframework.stereotype.Component;
import pacman.game_manager.model.GameStatus;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
public class GamePublisher implements Consumer<FluxSink<GameStatus>> {
    private final BlockingQueue<GameStatus> queue = new LinkedBlockingQueue<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public boolean push(GameStatus value) {
        return queue.offer(value);
    }

    @Override
    public void accept(FluxSink<GameStatus> booleanFluxSink) {
        this.executor.execute(() -> {
            while (true) {
                try {
                    GameStatus val = queue.take();
                    booleanFluxSink.next(val);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
