package web;

import annotation.*;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BeanFactory {

    private static final Logger log = LoggerFactory.getLogger(BeanFactory.class);
    private Map<String, HandlerExecution> controllers;
    private Map<String, Object> beans;
    private static BeanFactory beanFactory;
    public static Reflections reflections;

    private BeanFactory(Map<String, Object> beans) {
        this.beans = beans;
        this.controllers = new HashMap<>();
    }

    public static BeanFactory getInstance() {
        if (beanFactory == null) {
            beanFactory = BeanFactory.of().init();
            return beanFactory;
        }
        return beanFactory;
    }

    private static BeanFactory of() {
        URL packageDirURL = Thread.currentThread().getContextClassLoader().getResource("./");
        reflections = new Reflections(packageDirURL);
        Map<String, Object> beans = new HashMap<>();
        try {
            beans = createBean(beans);
            inject(beans);
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        log.debug("Hey Beans : {}", beans.values());
        return new BeanFactory(beans);
    }

    private BeanFactory init() {
        for (Class<?> clazz : reflections.getTypesAnnotatedWith(Controller.class)) {
            addHandler(clazz);
        }
        return this;
    }

    private static Map<String, Object> createBean(Map<String, Object> beans) throws Exception {
        for (Class<?> bean : findAnnotatedEntity()) {
            beans.put(bean.getName(), bean.newInstance());
        }
        return beans;
    }

    private static Set<Class<?>> findAnnotatedEntity() {
        Set<Class<?>> annotatedEntities = new HashSet<>();
        annotatedEntities.addAll(reflections.getTypesAnnotatedWith(Repository.class));
        annotatedEntities.addAll(reflections.getTypesAnnotatedWith(Controller.class));
        annotatedEntities.addAll(reflections.getTypesAnnotatedWith(Service.class));
        return annotatedEntities;
    }

    public void addHandler(Class<?> clazz) {
        try {
            if (clazz.isAnnotationPresent(RequestMapping.class)) {
                log.debug("requestMapping url : {}", clazz.getAnnotation(RequestMapping.class).value());
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    HandlerExecution handleExecution = new HandlerExecution(method, beans.get(clazz.getName()));
                    if (handleExecution.hasRequestAnnotation()) {
                        controllers.put(handleExecution.pullMethodRequest(), handleExecution);
                    }
                }
            }
        } catch (Exception e) {
            log.debug("addcontroller error");
            e.printStackTrace();
        }
    }

    private static void inject(Map<String, Object> beans) throws Exception {
        Set<String> keys = beans.keySet();
        for (String key : keys) {
            Object bean = beans.get(key);
            dependencyInjection(beans, bean);
        }
    }

    private static void dependencyInjection(Map<String, Object> beans, Object bean) throws Exception {
        for (Field field : bean.getClass().getDeclaredFields()) {
            field.setAccessible(true); // private access 허용
            if (field.isAnnotationPresent(Autowired.class)) {
                field.set(bean, beans.get(field.getType().getName()));
                log.debug("필드에 주입된 값과 beanFactory에 담긴 값은" + field.get(bean).equals(beans.get(field.getType().getName())));
            }
        }
    }


    public HandlerExecution getController(String requestUrl) {
        Set<String> set = controllers.keySet();
        for (String key : set) {
            if (requestUrl.startsWith(key) && !requestUrl.contains(".")) {
                return controllers.get(key);
            }
        }
        return null;
    }
}