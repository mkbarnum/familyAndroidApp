package Server;


import Database.Database;
import Handler.*;
import com.sun.net.httpserver.HttpServer;


import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {

    public static void main(String args[]) throws Exception{
        Database db = new Database();
        db.openConnection();
        db.createTables();
        db.closeConnection(true);

        try {
            startServer(Integer.parseInt(args[0]));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void startServer(int port) throws IOException {
        InetSocketAddress serverAddress = new InetSocketAddress(port);
        HttpServer server = HttpServer.create(serverAddress, 10);
        registerHandlers(server);
        server.start();
    }

    private static void registerHandlers(HttpServer server){
        server.createContext("/", new FileHandler());
        server.createContext("/clear", new ClearRequestHandler());
        server.createContext("/load", new LoadRequestHandler());
        server.createContext("/user/login", new LoginRequestHandler());
        server.createContext("/event", new EventRequestHandler());
        server.createContext("/person", new PersonRequestHandler());
        server.createContext("/user/register", new RegisterRequestHandler());
        server.createContext("/fill", new FillRequestHandler());
    }
}
