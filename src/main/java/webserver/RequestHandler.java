package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.FrontController;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler extends Thread {
    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);

    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}", connection.getInetAddress(),
                connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpRequest request = HttpFactory.init(in);
            HttpResponse response = new HttpResponse(out);

            FrontController.dispatch(request, response);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
