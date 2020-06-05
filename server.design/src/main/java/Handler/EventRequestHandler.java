package Handler;

import Model.AuthToken;
import Request.EventRequest;
import Result.EventResult;
import Service.eventService;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class EventRequestHandler implements HttpHandler {
    public void handle(HttpExchange exchange){
            EventRequest r = new EventRequest();
            eventService eService = new eventService();
            EventResult result;
            try {

                if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                    Headers reqHeaders = exchange.getRequestHeaders();

                    if (reqHeaders.containsKey("Authorization")) {
                        String authToken = reqHeaders.getFirst("Authorization");
                        if (Authorization.tokenExists(authToken)) {
                            AuthToken token = Authorization.getToken(authToken);
                            String username = token.getUsername();

                            String urlPath = exchange.getRequestURI().toString();

                            if (urlPath.equals("/event")) {
                                r.setUsername(username);
                                result = eventService.findEvent(r);

                            } else {
                                String eventID = urlPath.replace("/event/", "");
                                r.setUsername(username);
                                r.setEventID(eventID);
                                result = eventService.findEvent(r);
                            }
                            String json = Serializer.serialize(result);

                            exchange.sendResponseHeaders(200, 0);
                            OutputStream respBody = exchange.getResponseBody();
                            respBody.write(json.getBytes("UTF-8"));
                        }
                        else {
                            result = new EventResult("Authorization Token not found");
                            String json = Serializer.serialize(result);

                            exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
                            OutputStream respBody = exchange.getResponseBody();
                            respBody.write(json.getBytes("UTF-8"));
                        }
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }

            exchange.close();
    }


}
