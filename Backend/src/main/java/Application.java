import com.sun.net.httpserver.HttpServer;
import exceptions.EmailSenderException;
import handlers.RequestHandler;
import models.Flower;
import utils.EmailSender;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Application {

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api", new RequestHandler());
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
        System.out.println("Server started on port 8080");
    }

//    This is a test for emails part
//    public static void main(String[] args) {
//        try {
//            EmailSender.sendEmail("cristimadalincerbu@gmail.com", new Flower());
//        } catch (EmailSenderException e) {
//            System.out.println(e.getMessage());
//        }
//    }
}
