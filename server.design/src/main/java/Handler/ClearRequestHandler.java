package Handler;

import Result.ClearResult;
import Service.clearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class ClearRequestHandler implements HttpHandler {
    public void handle(HttpExchange exchange){

        clearService clearAll = new clearService();
        ClearResult result = clearAll.clear();

        String json = Serializer.serialize(result);
        try {
            exchange.sendResponseHeaders(200, 0);
            OutputStream respBody = exchange.getResponseBody();
            respBody.write(json.getBytes("UTF-8"));
            exchange.close();
        } catch (IOException e){
            System.out.println("Couldn't clear");
        }

    }
}
