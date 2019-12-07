package pacman.player_manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import pacman.player_manager.client.GameManagerClient;
import pacman.player_manager.client.SessionManagerClient;
import pacman.player_manager.model.PlayerAction;
import pacman.player_manager.model.Session;
import pacman.player_manager.model.User;
import pacman.player_manager.publisher.OpeningSessionPublisher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@EnableAsync
public class PlayerSessionService {

    private static final Logger LOG = LoggerFactory.getLogger(PlayerSessionService.class);

    @Autowired
    private OpeningSessionPublisher sessionPublisher;

    @Autowired
    private SessionManagerClient sessionManagerClient;

    @Autowired
    private GameManagerClient gameManagerClient;

    @Async
    public void createSession(List<User> playerQueue) {
        List<User> players = new ArrayList<>();
        for (int i = 0; i < 4; ++i) {
            players.add(playerQueue.get(0));
            playerQueue.remove(0);
        }
        LOG.info("Start game for players: " + players);
        String sessionId = sessionManagerClient.openSession(Mono.just(players)).block();
        Session session = sessionManagerClient.getSessionById(sessionId).block();
        LOG.info("Session was opened: " + session);
        sessionPublisher.push(session);
    }
}
