package util;

import db.DataBase;

import java.lang.reflect.Field;

public class DIUtils {

    public static Object injectDatabase(Class<?> clazz) throws Exception {
        Object target = clazz.newInstance();
        Field field = target.getClass().getDeclaredField("dataBase");
        field.setAccessible(true);
        field.set(target, new DataBase());
        return target;
    }
}
