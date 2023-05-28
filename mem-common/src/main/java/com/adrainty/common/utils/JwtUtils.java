package com.adrainty.common.utils;

import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/24 22:53
 */

public class JwtUtils {

    private JwtUtils() {}

    /**
     * 私钥， 可以用test里面的方法生成
     */
    private static final String SECRET_KEY = "fDpVe3gBDm1r4jArTAAtBofsXmUx0M";

    /**
     * token过期时间, 当前设置2h
     */
    private static final long EXPIRE_TIME = 2L * 60 * 60 * 1000;

    public static String createJWT(long id, String subject) {
        JwtBuilder builder = Jwts.builder()
                .setId(String.valueOf(id))
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, generalKey())
                .compressWith(CompressionCodecs.DEFLATE);
        if (EXPIRE_TIME > 0) {
            builder.setExpiration(new Date(System.currentTimeMillis() + EXPIRE_TIME));
        }
        return builder.compact();
    }

    public static Claims parseJWT(String jwtString) {
        return Jwts.parser().setSigningKey(generalKey())
                .parseClaimsJws(jwtString)
                .getBody();
    }

    private static SecretKey generalKey() {
        byte[] encodeKey = Base64.getDecoder().decode(SECRET_KEY);
        return new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES");
    }
}
