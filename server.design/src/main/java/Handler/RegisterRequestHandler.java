package Handler;

import Request.RegisterRequest;
import Result.RegisterResult;
import Service.registerService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RegisterRequestHandler implements HttpHandler {
    public void handle(HttpExchange exchange){
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqString = IStoString.readString(reqBody);


                RegisterRequest regRequest = Deserializer.deserialize(reqString,RegisterRequest.class);
                registerService regService = new registerService();
                RegisterResult result = regService.register(regRequest);
                String json = Serializer.serialize(result);

                exchange.sendResponseHeaders(200, 0);
                OutputStream respBody = exchange.getResponseBody();
                respBody.write(json.getBytes("UTF-8"));


                exchange.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        exchange.close();
    }
}
