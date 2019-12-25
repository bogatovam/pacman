package pacman.session_manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pacman.session_manager.client.GameManagerClient;
import pacman.session_manager.model.*;
import pacman.session_manager.publisher.ChangingSessionPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SessionService {

    private static final Logger LOG = LoggerFactory.getLogger(SessionService.class);

    private Map<String, Session> openedSessions = new HashMap<>();

    @Autowired
    private ChangingSessionPublisher sessionChangingPublisher;

    @Autowired
    private GameManagerClient gameManagerClient;

    public Flux<Session> getAllSessions() {
        LOG.info("Get all sessions");
        return Flux.fromIterable(openedSessions.values());
    }

    public Mono<Session> getSessionById(String id) {
        LOG.info("Get session: id=" + id);
        return openedSessions.containsKey(id) ?
                Mono.just(openedSessions.get(id))
                    .doOnNext(session -> LOG.info("Find session: " + session)):
                Mono.error(new Exception("Not such session: id=" + id));
    }

    public Mono<String> openSession(List<User> players) {
        LOG.info("Open session: payers=" + players);
        Session sess = new Session(players);
        return Mono.just(sess)
                .flatMap(session -> gameManagerClient.createGame(Mono.just(session.getPlayers()))
                        .flatMap(gameId -> gameManagerClient.getGameState(gameId)
                                .doOnNext(gameState -> session.setGameState(gameState))
                                .map(gameState -> session)))
                .doOnNext(session -> openedSessions.put(session.getId(), session))
                .doOnNext(session -> LOG.info("Open session: " + session))
                .map(Session::getId);
    }

    public Mono<Session> closeSession(String id) {
        LOG.info("Close session: id=" + id);
        return getSessionById(id)
                .flatMap(session -> gameManagerClient.closeGame(session.getGameState().getId())
                        .doOnNext(gameState -> session.setGameState(gameState))
                        .map(gameState -> session))
                .doOnNext(session -> LOG.info("Session was closed: " + session));
    }

    public Mono<User> addWatcher(String sessionId, String userId) {
        LOG.info("Add watcher: sessionId=" + sessionId + ", userId=" + userId);
        return getSessionById(sessionId).flatMap(session -> Mono.just(new User(userId, "Watcher"))
                        .flatMap(user -> !session.getWatchers().contains(user) && !session.getPlayers().contains(user) ?
                                Mono.just(user)
                                        .doOnNext(watcher -> session.getWatchers().add(watcher))
                                        .doOnNext(watcher -> sessionChangingPublisher.push(new SessionStatus(session, SessionStatus.Action.CHANGE)))
                                        .doOnNext(watcher -> LOG.info("Watcher was added: sessionId=" + sessionId + ", user=" + watcher)) :
                                Mono.error(new Exception("User already exist in session: sessionId=" + sessionId + ", userId=" + userId))));
    }

    public Mono<User> removeWatcher(String sessionId, String userId) {
        LOG.info("Remove watcher: sessionId=" + sessionId + ", userId=" + userId);
        return getSessionById(sessionId)
                .flatMap(session -> Mono.just(new User(userId, userId))
                    .flatMap(user -> session.getWatchers().contains(user) ?
                            Mono.just(user)
                                .doOnNext(deletedUser -> session.getWatchers().remove(deletedUser))
                                .doOnNext(deletedUser -> sessionChangingPublisher.push(new SessionStatus(session, SessionStatus.Action.CHANGE)))
                                .doOnNext(deletedUser -> LOG.info("Watcher was added: sessionId=" + sessionId + ", user=" + deletedUser)) :
                            Mono.error(new Exception("User doesn't exist in session: sessionId=" + sessionId + ", userId=" + userId))));
    }

    public void updateGameState(GameStatus gameStatus) {
        LOG.debug("Update gameState: " + gameStatus);
        String sessionId = openedSessions.values().stream()
                .filter(session -> session.getGameState().getId().equals(gameStatus.getGameState().getId()))
                .findFirst().map(Session::getId).orElse(null);
        if (sessionId != null) {
            openedSessions.get(sessionId).setGameState(gameStatus.getGameState());
            SessionStatus.Action action = gameStatus.getStatus().equals(GameStatus.Status.REMOVED) ?
                    SessionStatus.Action.REMOVE : SessionStatus.Action.CHANGE;
            if (action == SessionStatus.Action.REMOVE)
                openedSessions.remove(sessionId);
            sessionChangingPublisher.push(new SessionStatus(openedSessions.get(sessionId), action));
        }
    }
}
