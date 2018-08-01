package com.example.user8.myapp_admin;

import java.util.HashMap;
import java.util.Map;

public class usermodel {

    String u_email,u_username,key;

    public usermodel(){}

    public usermodel(String u_email,String u_username,String key) {
        this.key = key;
        this.u_email = u_email;
        this.u_username = u_username;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getU_username() {
        return u_username;
    }

    public void setU_username(String u_username) {
        this.u_username = u_username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("u_email",u_email);
        result.put("u_username",u_username);
        result.put("key",key);

        return  result;
    }

}
