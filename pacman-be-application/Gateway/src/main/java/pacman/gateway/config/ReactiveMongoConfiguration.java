package pacman.gateway.config;

import com.mongodb.MongoClientURI;
import com.mongodb.reactivestreams.client.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

@Configuration
public class ReactiveMongoConfiguration {
    @Autowired
    private MongoClient mongoClient;

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Bean
    public ReactiveMongoTemplate reactiveMongoTemplate() {
        return new ReactiveMongoTemplate(mongoClient, new MongoClientURI(mongoUri).getDatabase());
    }
}
