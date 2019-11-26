package pacman.session_manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pacman.session_manager.model.Session;
import pacman.session_manager.model.SessionStatus;
import pacman.session_manager.model.User;
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
                .doOnNext(session -> openedSessions.put(session.getId(), session))
                .doOnNext(session -> LOG.info("Open session: " + session))
                .map(Session::getId);
    }

    public Mono<Session> closeSession(String id) {
        LOG.info("Close session: id=" + id);
        return getSessionById(id)
                .doOnNext(session -> sessionChangingPublisher.push(new SessionStatus(session, SessionStatus.Action.REMOVE)))
                .doOnNext(session -> openedSessions.remove(session.getId()))
                .doOnNext(session -> LOG.info("Session was closed: " + session));
    }

    public Mono<User> addWatcher(String sessionId, String userId) {
        LOG.info("Add watcher: sessionId=" + sessionId + ", userId=" + userId);
        return getSessionById(sessionId).flatMap(session -> !session.containUser(userId) ?
                Mono.just(new User(userId))
                        .doOnNext(user -> session.getWatchers().add(user))
                        .doOnNext(user -> sessionChangingPublisher.push(new SessionStatus(session, SessionStatus.Action.CHANGE)))
                        .doOnNext(user -> LOG.info("Watcher was added: sessionId=" + sessionId + ", user=" + user)) :
                Mono.error(new Exception("User already exist in session: sessionId=" + sessionId + ", userId=" + userId)));
    }

    public Mono<User> removeWatcher(String sessionId, String userId) {
        LOG.info("Remove watcher: sessionId=" + sessionId + ", userId=" + userId);
        return getSessionById(sessionId)
                .flatMap(session -> Mono.just(new User(userId))
                    .flatMap(user -> session.getWatchers().contains(user) ?
                            Mono.just(user)
                                .doOnNext(deletedUser -> session.getWatchers().remove(deletedUser))
                                .doOnNext(deletedUser -> sessionChangingPublisher.push(new SessionStatus(session, SessionStatus.Action.CHANGE)))
                                .doOnNext(deletedUser -> LOG.info("Watcher was added: sessionId=" + sessionId + ", user=" + deletedUser)) :
                            Mono.error(new Exception("User doesn't exist in session: sessionId=" + sessionId + ", userId=" + userId))));
    }
}
