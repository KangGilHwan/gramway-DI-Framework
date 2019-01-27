package web;

import annotation.AnnotationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

public class FrontController {

    private static final Logger log = LoggerFactory.getLogger(FrontController.class);

    public static void dispatch(HttpRequest request, HttpResponse response) throws Exception {
        BeanFactory beanFactory = BeanFactory.getInstance();
        HandlerExecution controller = beanFactory.getController(request.getUrl());
        if (controller == null) {
            log.debug("handler null");
            ForwardController.forward(request, response);
            return;
        }
        log.debug("Controller : {}", controller.getClass().getName());
        AnnotationHandler annotationHandler = new AnnotationHandler(request, response);
        annotationHandler.controllerHandle(controller);
    }
}
