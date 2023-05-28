package com.adrainty.authority;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.asymmetric.Sign;
import cn.hutool.crypto.asymmetric.SignAlgorithm;
import cn.hutool.crypto.digest.DigestUtil;
import com.adrainty.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/5/27 18:28
 */
public class TestAuthority {

    @Test
    public void testPassword() {
        String password = "123465";
        String salt = RandomStringUtils.randomAlphanumeric(20);
        System.out.println(salt);
        String encrypt = DigestUtil.sha256Hex(password + salt);
        assert DigestUtil.sha256Hex(password + salt).equals(encrypt);
    }

    @Test
    public void getKey() {
        String text = "123456";
        KeyPair pair = SecureUtil.generateKeyPair("RSA");
        PrivateKey privateKey = pair.getPrivate();
        PublicKey publicKey = pair.getPublic();
        String privateKeyStr = bytesToBase64(privateKey.getEncoded());
        System.out.println("私钥: " + privateKeyStr);
        String publicKeyStr = bytesToBase64(publicKey.getEncoded());
        System.out.println("公钥: " + publicKeyStr);

        RSA rsa = new RSA(privateKeyStr, publicKeyStr);
        byte[] encrypt = rsa.encrypt(StrUtil.bytes(text, CharsetUtil.CHARSET_UTF_8), KeyType.PublicKey);
        System.out.println("公钥加密：" + bytesToBase64(encrypt));

        byte[] decrypt = rsa.decrypt(encrypt, KeyType.PrivateKey);
        System.out.println("私钥解密：" + new String(decrypt,StandardCharsets.UTF_8));
    }

    @Test
    public void testJWT() {
        Claims abcdefg = JwtUtils.parseJWT("abcdefg");
        System.out.println(abcdefg.getId());
    }

    private static String bytesToBase64(byte[] bytes) {
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }
}
