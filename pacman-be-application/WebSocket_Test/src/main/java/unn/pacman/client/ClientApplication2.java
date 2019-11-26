package unn.pacman.client;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.JettyWebSocketClient;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;
import reactor.core.publisher.Mono;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URI;
import java.time.Duration;

public class ClientApplication2 extends JFrame {
    JLabel label;
    JButton button;
    URI settingURI = URI.create("http://localhost:8080/setMessage");
    URI gettingURI = URI.create("ws://localhost:8080/getMessage");
    WebSocketClient client;
    Thread socketThread;

    ClientApplication2() {
        setSize(505, 600);
        setLayout( new FlowLayout( FlowLayout.CENTER ));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon image = new ImageIcon(".\\src\\main\\resources\\pacman.png");
        JPanel background = new JPanel();
        background.setPreferredSize(new Dimension(500, 450));
        background.setBackground(Color.black);
        label = new JLabel(image);
        label.setPreferredSize(new Dimension(500, 450));
        button = new JButton("Press me");
        button.setPreferredSize(new Dimension(500, 50));
        add(background);
        background.add(label);
        add(button, BorderLayout.SOUTH);
        setVisible(true);
        startApp();
    }

    void startApp() {
        label.setVisible(false);
        button.addMouseListener(new ButtonListener());
        client = new ReactorNettyWebSocketClient();
        socketThread = new Thread() {
            @Override
            public void run() {
                client.execute(gettingURI, session -> session.receive()
                        .map(webSocketMessage -> webSocketMessage.getPayloadAsText())
                        .map(str -> Boolean.valueOf(str))
                        .doOnNext(boolValue -> System.out.println(boolValue))
                        .doOnNext(boolValue -> label.setVisible(boolValue))
                        .log()
                        .then())
                        .block();
            }
        };
        socketThread.start();
    }

    void pressing() {
        Boolean wasPressed = !label.isVisible();
        String request = WebClient.create().post().uri(settingURI)
                .accept(MediaType.APPLICATION_JSON)
                .syncBody(wasPressed)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
        System.out.println(request);
    }

    class ButtonListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) { pressing(); }

        @Override
        public void mouseReleased(MouseEvent e) { pressing(); }

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->new ClientApplication());
    }
}
