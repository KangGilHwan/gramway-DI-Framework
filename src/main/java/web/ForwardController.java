package web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.ContentType;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class ForwardController {

    private static final Logger log = LoggerFactory.getLogger(ForwardController.class);

    public static void forward(HttpRequest request, HttpResponse response) {
        String accept = request.getHeader("Accept");
        log.debug("Stylesheet! : {}", accept);
        if (accept != null && accept.contains("text/css")) {
            response.forward(request.getUrl(), ContentType.CSS);
            return;
        }
        response.forward(request.getUrl(), ContentType.HTML);
    }
}
