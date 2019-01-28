package annotation;

import org.junit.Test;
import util.DIUtils;
import web.CreateUserController;
import web.HandlerExecution;
import web.ListUserController;
import web.LoginController;
import webserver.HttpFactory;
import webserver.HttpRequest;
import webserver.HttpResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AnnotationHandlerTest {
    private String testDirectory = "./src/test/resources/";

    @Test
    public void parameterHandle() throws Exception {
        InputStream inputSteram = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = HttpFactory.init(inputSteram);
        HttpResponse response = new HttpResponse(new FileOutputStream(new File(testDirectory + "Http_Forward.txt")));


        AnnotationHandler handler = new AnnotationHandler(request, response);
        Method[] methods = CreateUserController.class.getMethods();
        CreateUserController createUserController = (CreateUserController) DIUtils.injectDatabase(CreateUserController.class);

        assertThat(handler.parameterHandle(new HandlerExecution(methods[0], createUserController)), is("redirect:/index.html"));
    }

    @Test
    public void parameterHandleNoRedirect() throws Exception {
        InputStream inputSteram = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = HttpFactory.init(inputSteram);
        HttpResponse response = new HttpResponse(new FileOutputStream(new File(testDirectory + "Http_Forward.txt")));

        AnnotationHandler handler = new AnnotationHandler(request, response);
        Method[] methods = ListUserController.class.getMethods();
        ListUserController listUserController = (ListUserController) DIUtils.injectDatabase(ListUserController.class);

        assertThat(handler.parameterHandle(new HandlerExecution(methods[0], listUserController)) == null, is(true));
    }

    @Test
    public void parameterHandleRedirect2() throws Exception {
        InputStream inputSteram = new FileInputStream(new File(testDirectory + "Http_GET.txt"));
        HttpRequest request = HttpFactory.init(inputSteram);
        HttpResponse response = new HttpResponse(new FileOutputStream(new File(testDirectory + "Http_Forward.txt")));

        AnnotationHandler handler = new AnnotationHandler(request, response);
        Method[] methods = LoginController.class.getMethods();
        LoginController loginController = (LoginController) DIUtils.injectDatabase(LoginController.class);

        assertThat(handler.parameterHandle(new HandlerExecution(methods[0], loginController)), is("redirect:/user/login_failed.html"));
    }
}