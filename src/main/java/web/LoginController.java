package web;

import annotation.Autowired;
import annotation.Controller;
import annotation.RequestMapping;
import annotation.RequestParam;
import db.DataBase;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.HttpResponse;

@Controller
@RequestMapping("/user/login")
public class LoginController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private DataBase dataBase;

    @RequestMapping("")
    public String login(@RequestParam("userId") String userId, @RequestParam("password") String password, HttpResponse response) {
        User user = dataBase.findUserById(userId);
        if (user == null || !user.matchPassword(password)) {
            response.addHeader("Set-Cookie", "logined=false; Path=/");
            log.debug("Login failed");
            return "redirect:/user/login_failed.html";
        }
        log.debug("Login Success");
        response.addHeader("Set-Cookie", "logined=true; Path=/");
        response.sendRedirect("/index.html");
        return "redirect:/index.html";
    }
}
