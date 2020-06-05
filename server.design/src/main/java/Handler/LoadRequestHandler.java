package Handler;

import Request.LoadRequest;
import Result.LoadResult;
import Service.loadService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoadRequestHandler implements HttpHandler {
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqString = IStoString.readString(reqBody);

                LoadRequest loadR = Deserializer.deserialize(reqString,LoadRequest.class);
                loadService loadS = new loadService();
                LoadResult result = loadS.load(loadR);

                String json = Serializer.serialize(result);

                exchange.sendResponseHeaders(200, 0);
                OutputStream respBody = exchange.getResponseBody();
                respBody.write(json.getBytes("UTF-8"));

                exchange.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }


}
