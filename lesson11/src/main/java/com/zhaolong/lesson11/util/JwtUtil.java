package com.zhaolong.lesson11.util;

import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Autowired
    private CustomFields customFields;

    public long EXPIRATION_TIME;
    public String JWT_SECRET;
    public String HEADER_STRING;
    public String TOKEN_PREFIX;
    public String USER_NAME;

    @PostConstruct
    private void init() {
        EXPIRATION_TIME = customFields.getExpirationTime();
        JWT_SECRET = customFields.getJwtSecret();
        HEADER_STRING = customFields.getHeaderString();
        TOKEN_PREFIX = customFields.getTokenPrefix()+" ";
        USER_NAME = customFields.getUserName();
    }




    /**
     * 生产Token
     *
     * @param userUuid
     */
    public String generateToken(String userUuid) {
        Map<String, Object> map = new HashMap<>();
        map.put(USER_NAME, userUuid);
        //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey();
        String jwt = Jwts.builder()//这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(map)//如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))//设置过期时间
                .signWith(SignatureAlgorithm.HS512, key)//设置签名使用的签名算法和签名使用的秘钥
                .compact();//就开始压缩
        return TOKEN_PREFIX + jwt; //jwt前面一般都会加Bearer
    }

    /**
     * 验证Header
     *
     * @param request
     */
    public void validateTokenAddUserIdToHeader(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        parseJWT(token);
    }

    /**
     * 解密jwt
     *
     * @param token
     */
    public Claims parseJWT(String token) {
        Claims claims;
        if (token != null) {
            try {
                //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
                SecretKey key = generalKey(); //签名秘钥，和生成的签名的秘钥一模一样
                claims = Jwts.parser()//得到DefaultJwtParser
                        .setSigningKey(key)//设置签名的秘钥
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();//设置需要解析的jwt
            } catch (ExpiredJwtException e) {
                throw new TokenValidationException("token expired");
            } catch (MalformedJwtException e) {
                throw new TokenValidationException("not right token");
            } catch (Exception e) {
                throw new TokenValidationException(e.getMessage());
            }
        } else {
            throw new TokenValidationException("Missing token");
        }
        return claims;
    }


    /**
     * 获取用户id
     *
     * @param jwt
     */
    public String getIdByJWT(String jwt) {
        Claims c = parseJWT(jwt);//注意：如果jwt已经过期了，这里会抛出jwt过期异常。
        return c.get(USER_NAME).toString();
    }

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public SecretKey generalKey() {
        //本地配置文件中加密的密文7786df7fc3a34e26a61c034d5ec8245d
        String stringKey = JWT_SECRET;
        //本地的密码解码[B@152f6e2
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        // 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }


    public class TokenValidationException extends RuntimeException {
        public TokenValidationException(String msg) {
            super(msg);
        }
    }
}
