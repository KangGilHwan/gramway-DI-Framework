package webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpFactory {

    private static final Logger log = LoggerFactory.getLogger(HttpFactory.class);

    public static HttpRequest init(InputStream in) throws IOException {
        InputStreamReader input = new InputStreamReader(in);
        BufferedReader request = new BufferedReader(input);
        List<String> methodAndUrl = Arrays.asList(request.readLine().split(" "));
        HttpMethod method = new HttpMethod(methodAndUrl.get(0));
        Map<String, String> headers = createHeader(request);
        HttpHeader httpHeader = new HttpHeader(headers);
        String url = methodAndUrl.get(1);
        return new HttpRequest(url, method, httpHeader, request);
    }

    private static Map<String, String> createHeader(BufferedReader request) throws IOException {
        String line = request.readLine();
        String[] parse;
        Map<String, String> header = new HashMap<>();
        while (!line.equals("")) {
            log.debug("header : {}", line);
            parse = parseHeader(line);
            header.put(parse[0].trim(), parse[1].trim());
            line = request.readLine();
        }
        return header;
    }

    private static String[] parseHeader(String line) {
        return line.split(":");
    }
}
