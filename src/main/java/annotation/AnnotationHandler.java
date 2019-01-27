package annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ReflectionUtils;
import web.HandlerExecution;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.util.ArrayList;
import java.util.List;

public class AnnotationHandler {

    private static final Logger log = LoggerFactory.getLogger(AnnotationHandler.class);
    private HttpRequest request;
    private HttpResponse response;

    public AnnotationHandler(HttpRequest request, HttpResponse response) {
        this.request = request;
        this.response = response;
    }

    public void controllerHandle(HandlerExecution controller) throws Exception {
        processResponse(parameterHandle(controller));
    }

    public void processResponse(String location) {
        log.debug("location : {}", location);
        if (location != null) {
            if (location.startsWith("redirect:")) {
                response.sendRedirect(location.substring(location.indexOf(":") + 1));
            }
        }
    }

    public String parameterHandle(HandlerExecution controller) throws Exception {
        List<Object> params = new ArrayList<>();
        try {
            ReflectionUtils reflectionUtils = new ReflectionUtils(controller.pullParameters());
            params = reflectionUtils.makeParams(params, request, response);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return controller.execute(params);
    }

}