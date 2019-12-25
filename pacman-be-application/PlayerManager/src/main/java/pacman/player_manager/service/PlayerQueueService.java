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

    private List<User> playerQueue = new CopyOnWriteArrayList<>(); // List of players

    public Mono<User> addIntoQueue(String id) {
        return Mono.just(id)
                .doOnNext(userId -> LOG.info("Regist player: userId=" + userId))
                // TODO: вытаскивать польователей из базы
                .map(userId -> new User(userId, "Player" + playerQueue.size()))
                .flatMap(user -> !playerQueue.contains(user) ?
                        Mono.just(user)
                            .doOnNext(player -> playerQueue.add(player))
                            .doOnNext(player -> LOG.info("Player was created: user=" + player))
                            .doOnNext(player -> checkForFullSet()) :
                        Mono.error(new Exception("Player with user=" + user + " already exist")));
    }

    public Mono<User> removeFromQueue(String id) {
        return Mono.just(id)
                .doOnNext(userId -> LOG.info("Remove user: userId=" + userId))
                .map(userId -> new User(userId, userId))
                .flatMap(user -> playerQueue.contains(user) ?
                        Mono.just(user)
                                .doOnNext(player -> playerQueue.remove(player))
                                .doOnNext(player -> LOG.info("Player was removed: user=" + player)) :
                        Mono.error(new Exception("Player with user=" + user + " doesn't exist")));
    }

    private void checkForFullSet() {
        if (playerQueue.size() >= 4) {
            sessionService.createSession(playerQueue);
        }
    }
}
