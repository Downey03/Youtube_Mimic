package Utilities;

import  DTO.UserDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;

public class JwtUtil {
    private final static String clientSecret = "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";
    static Algorithm algorithm = Algorithm.HMAC256(clientSecret);

    public static String generateToken(String userId,String userEmail) {
        return JWT.create()
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withClaim("userId", userId)
                .withClaim("userEmail",userEmail)
                .sign(algorithm);
    }

    public static DecodedJWT decodeToken(String tkn){
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(tkn);
    }
    public static boolean verifyToken(String tkn){
        DecodedJWT decodedTkn = decodeToken(tkn);
        return decodedTkn != null && !decodedTkn.getClaim("userId").isMissing() ;
    }

    public static String getUserEmail(String tkn){
        DecodedJWT decodedJWT = decodeToken(tkn);
        return decodedJWT.getClaim("userEmail").asString();
    }
}
