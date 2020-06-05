package Handler;

import Model.AuthToken;
import Request.PersonRequest;
import Result.PersonResult;
import Service.personService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class PersonRequestHandler implements HttpHandler {
    public void handle(HttpExchange exchange){
        PersonRequest r = new PersonRequest();
        personService pService = new personService();
        PersonResult result;
        try {

            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");
                    if (Authorization.tokenExists(authToken)) {
                        AuthToken token = Authorization.getToken(authToken);
                        String username = token.getUsername();

                        String urlPath = exchange.getRequestURI().toString();
                        if (urlPath.equals("/person")) {
                            r.setUsername(username);
                            result = personService.findPerson(r);
                        } else {
                            String personID = urlPath.replace("/person/", "");
                            r.setUsername(username);
                            r.setPersonID(personID);
                            result = personService.findPerson(r);
                        }
                        String json = Serializer.serialize(result);

                        exchange.sendResponseHeaders(200, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        respBody.write(json.getBytes("UTF-8"));
                    }
                    else {
                        result = new PersonResult("Authorization Token not found");
                        String json = Serializer.serialize(result);

                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
                        OutputStream respBody = exchange.getResponseBody();
                        respBody.write(json.getBytes("UTF-8"));
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        exchange.close();
    }
}
