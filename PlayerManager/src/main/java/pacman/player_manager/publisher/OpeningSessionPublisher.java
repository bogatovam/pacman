package pacman.player_manager.publisher;

import org.springframework.stereotype.Component;
import pacman.player_manager.model.Session;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
public class OpeningSessionPublisher implements Consumer<FluxSink<Session>> {
    private final BlockingQueue<Session> queue = new LinkedBlockingQueue<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public boolean push(Session value) {
        return queue.offer(value);
    }

    @Override
    public void accept(FluxSink<Session> booleanFluxSink) {
        this.executor.execute(() -> {
            while (true) {
                try {
                    Session val = queue.take();
                    booleanFluxSink.next(val);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
