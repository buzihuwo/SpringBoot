package com.zhaolong.lesson11.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

public class JwtUtil {
    public static final long EXPIRATION_TIME = 1000*60*10;
    static final String  JWT_SECRET = "7786df7fc3a34e26a61c034d5ec8245d";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String USER_NAME = "userName";

    public static String generateToken(String username){
        HashMap<String, Object> map = new HashMap<>();
        map.put(USER_NAME,username);
        //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey();
        String jwt =Jwts.builder()//这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(map)//如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))//设置过期时间
                .signWith(SignatureAlgorithm.HS512,key)//设置签名使用的签名算法和签名使用的秘钥
                .compact();//就开始压缩
        return TOKEN_PREFIX+jwt; //jwt前面一般都会加Bearer
    }

    public static HttpServletRequest validateTokenAddUserIdToHeader(HttpServletRequest request){
        String token = request.getHeader(HEADER_STRING);
        if(token!=null){
            try{
                //生成签名的时候使用的秘钥secret,这个方法本地封装了的，一般可以从本地配置文件中读取，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
                SecretKey key = generalKey();
                Map<String,Object> body=Jwts.parser()//得到DefaultJwtParser
                        .setSigningKey(key)//设置签名的秘钥
                        .parseClaimsJws(token.replace(TOKEN_PREFIX,""))
                        .getBody();//设置需要解析的jwt
                return new CustomHttpServletRequest(request, body);
            }catch (ExpiredJwtException e){
                throw new TokenValidationException("token expired");
            }
            catch (Exception e){
                throw new TokenValidationException(e.getMessage());
            }
        }else {
            throw new TokenValidationException("Missing token");
        }
    }

    /**
     * 由字符串生成加密key
     * @return
     */
    public static SecretKey generalKey(){
        //本地配置文件中加密的密文7786df7fc3a34e26a61c034d5ec8245d
        String stringKey = JWT_SECRET;
        //本地的密码解码[B@152f6e2
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        //[B@152f6e2
//        System.out.println(encodedKey);
        //7786df7fc3a34e26a61c034d5ec8245d
//        System.out.println(Base64.encodeBase64URLSafeString(encodedKey));
        // 根据给定的字节数组使用AES加密算法构造一个密钥，使用 encodedKey中的始于且包含 0 到前 leng 个字节这是当然是所有。（后面的文章中马上回推出讲解Java加密和解密的一些算法）
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
//        System.out.println(key+"///");
        return key;
    }

    public static class CustomHttpServletRequest extends HttpServletRequestWrapper {
        private Map<String, String> claims;

        public CustomHttpServletRequest(HttpServletRequest request, Map<String, ?> claims) {
            super(request);
            this.claims = new HashMap<>();
            claims.forEach((k, v) -> this.claims.put(k, String.valueOf(v)));
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            if (claims != null && claims.containsKey(name)) {
                return Collections.enumeration(Arrays.asList(claims.get(name)));
            }
            return super.getHeaders(name);
        }

        public Map<String, String> getClaims() {
            return claims;
        }
    }

    static class TokenValidationException extends RuntimeException {
        public TokenValidationException(String msg) {
            super(msg);
        }
    }
}
