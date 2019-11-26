package pacman.player_manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import pacman.player_manager.model.User;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PlayerQueueService {

    @Autowired
    private PlayerSessionService sessionService;

    private static final Logger LOG = LoggerFactory.getLogger(PlayerQueueService.class);

    private List<String> playerQueue = new CopyOnWriteArrayList<>(); // List of playerId

    public Mono<String> addIntoQueue(String id) {
        return Mono.just(id)
                .doOnNext(userId -> LOG.info("Regist player: userId=" + userId))
                .flatMap(userId -> !playerQueue.contains(userId) ?
                        Mono.just(userId)
                            .doOnNext(playerId -> playerQueue.add(playerId))
                            .doOnNext(playerId -> LOG.info("Player was created: userId=" + userId))
                            .doOnNext(playerId -> checkForFullSet()) :
                        Mono.error(new Exception("Player with userId=" + userId + " already exist")));
    }

    public Mono<String> removeFromQueue(String id) {
        return Mono.just(id)
                .doOnNext(userId -> LOG.info("Remove user: userId=" + userId))
                .flatMap(userId -> playerQueue.contains(userId) ?
                        Mono.just(userId)
                                .doOnNext(playerId -> playerQueue.remove(playerId))
                                .doOnNext(playerId -> LOG.info("Player was removed: userId=" + userId)) :
                        Mono.error(new Exception("Player with userId=" + userId + " doesn't exist")));
    }

    private void checkForFullSet() {
        if (playerQueue.size() >= 4) {
            sessionService.createSession(playerQueue);
        }
    }
}
