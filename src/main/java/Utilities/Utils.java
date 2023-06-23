package Utilities;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {


//    public static String EncodeString(HttpServletRequest req){
//        String id = ((req.getHeader("Authorization")).split(" ")[1]);
//
//        char[] userIdArray = id.toCharArray();
//
//        for(int i = 0 ; i < userIdArray.length ; i++){
//            userIdArray[i] = userIdArray[i] + Char.va;
//        }
//
//        return
//    }
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

    public static String getUserId(HttpServletRequest req) {
        return JwtUtil.getUserId((req.getHeader("Authorization")).split(" ")[1]);
    }

    public static String passwordEncrypt(String password)  {

        final byte[] passwordBytes = password.getBytes();
        StringBuffer encryptedPassword = new StringBuffer();
        try {
            MessageDigest MD = MessageDigest.getInstance("MD5");
            MD.update(passwordBytes);
            final byte[] messageDigest = MD.digest();

            for(byte m : messageDigest){
                String hex = Integer.toHexString(0xFF & m);
                if(hex.length()==1) encryptedPassword.append("0");
                encryptedPassword.append(m);
            }
        }catch (NoSuchAlgorithmException e){
            System.out.println(e.getMessage());
        }

        return encryptedPassword.toString();
    }
}
