import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ForwardControllerTest {

    @Test
    public void mapTest() {
        Map<String, String> test = new HashMap<>();
        test.put("test", "1");
        test.put("test1", "2");
        test.put("test2", "3");

        System.out.println(test.get("test5"));
    }
}
