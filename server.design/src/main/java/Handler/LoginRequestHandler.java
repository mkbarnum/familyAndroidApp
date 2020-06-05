package Handler;

import Request.LoginRequest;
import Result.LoginResult;
import Service.loginService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LoginRequestHandler implements HttpHandler {
    public void handle(HttpExchange exchange){
        try {
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqString = IStoString.readString(reqBody);

                LoginRequest loginRequest = Deserializer.deserialize(reqString,LoginRequest.class);
                loginService loginService = new loginService();
                LoginResult loginResult = loginService.login(loginRequest);

                String json = Serializer.serialize(loginResult);

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
