package webserver;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dto.UserDto;
import util.DIUtils;
import web.CreateUserController;
import web.HandlerExecution;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class HandlerExecutionTest {

    private HandlerExecution handlerExecution;
    private Method[] methods;

    @Before
    public void setUp() throws Exception{
        methods = CreateUserController.class.getMethods();
        handlerExecution = new HandlerExecution(methods[0], DIUtils.injectDatabase(CreateUserController.class));
    }

    @Test
    public void pullClassRequest() throws Exception {
        assertThat(	handlerExecution.pullClassRequest(), is("/user/create"));
    }
    @Test
    public void pullMethodRequest() throws Exception {
        assertThat(	handlerExecution.pullMethodRequest(), is("/user/create"));
    }

    @Test
    public void execute() throws Exception{
        List<Object> params = new ArrayList<>();
        params.add(new UserDto());
        assertThat(handlerExecution.execute(params), is("redirect:/index.html"));
    }
}