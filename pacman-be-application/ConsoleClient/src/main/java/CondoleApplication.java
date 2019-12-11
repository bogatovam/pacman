import ch.qos.logback.classic.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class CondoleApplication {

    private static String playerURL = "localhost:9090";
    private static String sessionURL = "localhost:8081";

    public static void main(String[] args) throws InterruptedException {
        ((ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME))
                .setLevel(Level.INFO);

        String userId = UUID.randomUUID().toString();
        System.out.println("Hello, you id is: " + userId);
        {
            System.out.println("You can:\n" +
                    "1. Regist on new game\n" +
                    "2. Watch game");
            Scanner scanner = new Scanner(System.in);
            System.out.print("You want: ");
            int val = scanner.nextInt();
            if (val == 1)
                newGame(userId);
            else if (val == 2)
                watchGame(userId);
        } while (true);
    }

    private static void newGame(String userId) throws InterruptedException {
        WebClient playerClient = WebClient.create("http://" + playerURL);
        ClientResponse responce =  playerClient.get().uri("/queue/{userId}", userId).exchange().block();
        if (responce.statusCode().value() != 200) {
            System.out.println("Error while regist client: " + responce.statusCode().value() + ", " + responce.bodyToMono(String.class).block());
            return;
        }
        System.out.println("User was registed");
        AtomicReference<String> resultForConnecting = new AtomicReference<>("");
        Thread waitOpeningSession = new Thread() {
            @Override
            public void run() {
                WebSocketClient client = new ReactorNettyWebSocketClient();
                client.execute(URI.create("ws://" + playerURL + "/wait?userId=" + userId), session -> session.receive()
                        .map(webSocketMessage -> webSocketMessage.getPayloadAsText())
                        .doOnNext(str -> System.out.println("Opening Socket return result: " + str))
                        .doOnNext(str -> resultForConnecting.set(str))
                        .then()
                        .log())
                        .block();
            }
        };
        waitOpeningSession.start();
        while (resultForConnecting.get().equals(""));
        waitOpeningSession.stop();
        String sessionId = resultForConnecting.get();
        System.out.println("Session was created: " + sessionId);
        checkSession(sessionId);
    }

    private static void watchGame(String userId) {
        WebClient sessionClient = WebClient.create("http://" + sessionURL);
        ClientResponse responce =  sessionClient.get().uri("/session").exchange().block();
        if (responce.statusCode().value() != 200) {
            System.out.println("Error while getting sessions: " + responce.statusCode().value() + ", " + responce.bodyToMono(String.class).block());
            return;
        }
        System.out.println("Opened sessions: " + responce.bodyToMono(String.class).block());
        String sessionId;
        System.out.println("Choose session: ");
        Scanner scanner = new Scanner(System.in);
        sessionId = scanner.nextLine();
        responce =  sessionClient.get().uri("/session/{sessionId}/user/{userId}", sessionId, userId).exchange().block();
        if (responce.statusCode().value() != 200) {
            System.out.println("Error while connect to session: " + responce.statusCode().value() + ", " + responce.bodyToMono(String.class).block());
            return;
        }
        System.out.println("You connected to session");
        responce =  sessionClient.get().uri("/session/{sessionId}", sessionId, userId).exchange().block();
        if (responce.statusCode().value() != 200) {
            System.out.println("Error while getting session: " + responce.statusCode().value() + ", " + responce.bodyToMono(String.class).block());
            return;
        }
        System.out.println("Now session is: " + responce.bodyToMono(String.class).block());
        checkSession(sessionId);
    }

    private static void checkSession(String sessionId) {
        AtomicReference<String> resultForClosing = new AtomicReference<>("");
        Thread waitChangingSession = new Thread() {
            @Override
            public void run() {
                WebSocketClient client = new ReactorNettyWebSocketClient();
                client.execute(URI.create(sessionURL + "/change?sessionId=" + sessionId), session -> session.receive()
                        .map(webSocketMessage -> webSocketMessage.getPayloadAsText())
                        .doOnNext(str -> System.out.println("Changing Socket return result: " + str))
                        .doOnNext(str -> resultForClosing.set(str))
                        .then()
                        .log())
                        .block();
            }
        };
        waitChangingSession.start();
    }
}
