package pacman.game_manager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pacman.game_manager.model.*;
import pacman.game_manager.publisher.GamePublisher;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameService {
    private static final Logger LOG = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private GamePublisher gamePublisher;

    private Map<String, GameThread> games = new HashMap<>();

    public Mono<GameState> getGame(String id) {
        LOG.info("Get gameState: id=" + id);
        return games.containsKey(id) ?
                Mono.just(games.get(id))
                    .map(GameThread::getGameState)
                    .doOnNext(gameState -> LOG.info("Find gameState: " +gameState)) :
                Mono.error(new Exception("Not such session: id=" + id));
    }

    public Mono<String> createGame(List<User> players) {
        LOG.info("Create gameState: players=" + players);
        try {
            return Mono.just(new GameState(players))
                    .map(gameState -> new GameThread("Game=" + gameState.getId(), gameState, gamePublisher))
                    .doOnNext(Thread::start)
                    .doOnNext(gameThread -> games.put(gameThread.getGameState().getId(), gameThread))
                    .map(GameThread::getGameState)
                    .doOnNext(gameState -> LOG.info("GameState was created: " + gameState))
                    .map(GameState::getId);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }

    public Mono<GameState> closeGame(String id) {
        LOG.info("Close gameState: id=" + id);
        return games.containsKey(id) ?
                Mono.just(games.get(id))
                        .doOnNext(Thread::stop)
                        .map(GameThread::getGameState)
                        .doOnNext(gameState -> games.remove(gameState.getId()))
                        .doOnNext(gameState -> gamePublisher.push(new GameStatus(gameState, GameStatus.Status.REMOVED)))
                        .doOnNext(gameState -> LOG.info("GameState was closed: " +gameState)) :
                Mono.error(new Exception("Not such session: id=" + id));
    }

    public void playerEvent(String gameId, PlayerAction playerAction) {
        LOG.info("Regist player event: gameId=" + gameId + ", playerAction=" + playerAction);
        if (games.containsKey(gameId)) {
            GameState gameState = games.get(gameId).getGameState();
            gameState.update(playerAction);
            gamePublisher.push(new GameStatus(gameState, GameStatus.Status.CHANGED));
        }
    }
}
