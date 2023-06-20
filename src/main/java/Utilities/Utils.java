package Utilities;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class Utils {

    public static String getRequestString(HttpServletRequest request) throws IOException {

        StringBuilder jb = new StringBuilder();

        BufferedReader reader = request.getReader();

        String line;
        while((line = reader.readLine()) != null) {
            jb.append(line);
        }
        reader.close();

        return jb.toString();
    }

    public static String getEmail(HttpServletRequest req) {
        return JwtUtil.getUserEmail((req.getHeader("Authorization")).split(" ")[1]);
    }
}
