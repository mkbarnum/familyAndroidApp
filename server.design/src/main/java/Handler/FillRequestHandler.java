package Handler;

import Request.FillRequest;
import Result.FillResult;
import Service.fillService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class FillRequestHandler implements HttpHandler {
    public void handle(HttpExchange exchange){
        try {
            FillRequest fillRequest = new FillRequest();
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {

                String urlPath = exchange.getRequestURI().toString();
                String[] paths = urlPath.split("/");
                if(paths.length < 3 || paths.length > 4){
                    exchange.sendResponseHeaders(404, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    respBody.write("Invalid URL parameters".getBytes("UTF-8"));
                }
                else if(paths.length == 3) {
                    fillRequest.setUsername(paths[2]);
                    fillRequest.setHasGeneration(false);
                    fillService fillService = new fillService();
                    FillResult result = fillService.fill(fillRequest);
                    String json = Serializer.serialize(result);

                    exchange.sendResponseHeaders(200, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    respBody.write(json.getBytes("UTF-8"));
                }
                else{
                    fillRequest.setHasGeneration(true);
                    fillRequest.setUsername(paths[2]);
                    fillRequest.setGenerations(Integer.parseInt(paths[3]));
                    fillService fillService = new fillService();
                    FillResult result = fillService.fill(fillRequest);
                    String json = Serializer.serialize(result);

                    exchange.sendResponseHeaders(200, 0);
                    OutputStream respBody = exchange.getResponseBody();
                    respBody.write(json.getBytes("UTF-8"));
                }

                exchange.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        exchange.close();
    }
}
