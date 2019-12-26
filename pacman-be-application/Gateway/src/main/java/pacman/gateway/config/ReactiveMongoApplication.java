package pacman.gateway.config;

import com.mongodb.MongoClientURI;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EnableReactiveMongoRepositories
public class ReactiveMongoApplication extends AbstractReactiveMongoConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(ReactiveMongoApplication.class);

    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    @Value("${spring.data.mongodb.service}")
    private String mongoService;

    @Override
    protected String getDatabaseName() {
        return new MongoClientURI(mongoUri).getDatabase();
    }

    @Override
    public MongoClient reactiveMongoClient() {
        return MongoClients.create(mongoUri);
    }
}
