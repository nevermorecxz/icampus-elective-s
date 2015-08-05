package com.irengine.campus.security.xauth;

/**
 * token
 * @author huang
 *
 */
public class Token {

    private String token;
    
    private long expires;

    public Token(String token, long expires){
        this.token = token;
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public long getExpires() {
        return expires;
    }
    
}
