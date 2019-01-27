package web;

import annotation.Autowired;
import annotation.Controller;
import annotation.RequestMapping;
import db.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;
import webserver.ModelAndView;

@Controller
@RequestMapping("/user/list")
public class ListUserController {

    private static final Logger log = LoggerFactory.getLogger(ListUserController.class);

    @Autowired
    private DataBase dataBase;

    @RequestMapping("")
    public void showUsers(HttpRequest request, HttpResponse response) {
        String cookie = request.getHeader("Cookie");
        if (!cookie.contains("logined=true")) {
            response.sendRedirect("/login.html");
            return;
        }
        ModelAndView modelAndView = new ModelAndView("/user/list.html");
        log.debug("Users : {}", dataBase.findAll());
        modelAndView.setAttribute("user", dataBase.findAll());
        response.modelAndViewResponse(modelAndView);
    }
}
