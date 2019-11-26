package pacman.session_manager.publisher;

import org.springframework.stereotype.Component;
import pacman.session_manager.model.Session;
import pacman.session_manager.model.SessionStatus;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
public class ChangingSessionPublisher implements Consumer<FluxSink<SessionStatus>> {
    private final BlockingQueue<SessionStatus> queue = new LinkedBlockingQueue<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public boolean push(SessionStatus value) {
        return queue.offer(value);
    }

    @Override
    public void accept(FluxSink<SessionStatus> booleanFluxSink) {
        this.executor.execute(() -> {
            while (true) {
                try {
                    SessionStatus val = queue.take();
                    booleanFluxSink.next(val);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
