package unn.pacman.server.publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
public class MessagePublisher implements Consumer<FluxSink<Boolean>> {
    private final BlockingQueue<Boolean> queue = new LinkedBlockingQueue<>();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public boolean push(Boolean value) {
        return queue.offer(value);
    }

    @Override
    public void accept(FluxSink<Boolean> booleanFluxSink) {
        this.executor.execute(() -> {
            while (true) {
                try {
                    Boolean val = queue.take();
                    booleanFluxSink.next(val);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
