package com.example.musicserver.utils;

import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JWTUtil {
    private static final String SECRET = "musicapp-0607";
    private static final Algorithm ALGO = Algorithm.HMAC256(SECRET);
    private static final long EXPIRE_TIME = 15 * 60 * 1000;

    /**
     * 创建token
     * 
     * @param payload 用户负载
     * @return 成功返回token，失败返回null
     */

    public static String createToken(Map<String, Object> payload) {
        Date iat = new Date();
        Date exp = new Date(iat.getTime() + EXPIRE_TIME);

        String token = null;
        try {
            token = JWT.create()
                    .withIssuedAt(iat).withExpiresAt(exp)
                    .withPayload(payload)
                    .sign(ALGO);
        } catch (Exception e) {
            log.error("create token exception: " + e.getMessage());
            return null;
        }

        return token;
    }

    /**
     * 验证token
     * 
     * @param token
     * @return 成功返回DecodedJWT, 失败返回null
     */
    public static Map<String, Claim> verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(ALGO).build();
            return verifier.verify(token).getClaims();
        } catch (JWTVerificationException e) {
            log.error("verify token exception: " + e.getMessage());
            return null;
        }
    }
}
