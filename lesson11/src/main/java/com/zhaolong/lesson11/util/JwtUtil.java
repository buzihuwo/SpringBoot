package com.zhaolong.lesson11.util;

import java.util.*;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class JwtUtil {
    public static final long EXPIRATION_TIME = 1000*60*10;
    static final String  SECRET = "ThisIsASecret";
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String USER_NAME = "userName";

    public static String generateToken(String username){
        HashMap<String, Object> map = new HashMap<>();
        map.put(USER_NAME,username);
        String jwt =Jwts.builder()
                .setClaims(map)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512,SECRET)
                .compact();
        return TOKEN_PREFIX+jwt; //jwt前面一般都会加Bearer
    }

    public static HttpServletRequest validateTokenAddUserIdToHeader(HttpServletRequest request){
        String token = request.getHeader(HEADER_STRING);
        if(token!=null){
            try{
                Map<String,Object> body=Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX,""))
                        .getBody();
                return new CustomHttpServletRequest(request, body);
            }catch (Exception e){
                throw new TokenValidationException(e.getMessage());
            }
        }else {
            throw new TokenValidationException("Missing token");
        }
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
