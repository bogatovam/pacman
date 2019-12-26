package pacman.gateway.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pacman.gateway.model.User;
import pacman.gateway.repository.UserRepository;
import reactor.core.publisher.Mono;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public Mono<User> getUser(String userId) {
        LOG.info("Get user with id=" + userId);
        return Mono.just(userId)
                .flatMap(id -> userRepository.findById(id))
                .doOnNext(user -> LOG.info("Find user: " + user));
    }

    public Mono<User> createUser(User user) {
        LOG.info("Create new user=" + user);
        return Mono.just(user)
                .flatMap(newUser -> userRepository.save(user))
                .doOnNext(saveUser -> LOG.info("User was added, id=" + saveUser.getId()));
    }
}
