package Handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.nio.file.Files;

public class FileHandler implements HttpHandler {

    public void handle(HttpExchange exchange){

    try {
        if(exchange.getRequestMethod().toUpperCase().equals("GET")){
            String urlPath = exchange.getRequestURI().toString();

            if(urlPath == null || urlPath.equals("/")){
                urlPath = "/index.html";
            }

            String filePath = "web" + urlPath;
            File file = new File(filePath);
            if(file.exists()){
                exchange.sendResponseHeaders(200,0);
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(file.toPath(), respBody);
                exchange.close();
            }
            else{
                File failedFile = new File("web/HTML/404.html");
                exchange.sendResponseHeaders(200,0);
                OutputStream respBody = exchange.getResponseBody();
                Files.copy(file.toPath(),respBody);
                exchange.close();
            }
        }
    } catch(IOException e){
        e.printStackTrace();
    }

    }
}


