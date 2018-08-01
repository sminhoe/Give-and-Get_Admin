package com.example.user8.myapp_admin;

import java.util.HashMap;
import java.util.Map;

public class foodmodel {

    String f_name,f_status,f_key;

    public foodmodel(){}

    public foodmodel(String  f_name,String f_status, String f_key) {
        this.f_name = f_name;
        this.f_status = f_status;
        this.f_key = f_key;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("f_name",f_name);
        result.put("f_status",f_status);
        result.put("f_key",f_key);

        return  result;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getF_status() {
        return f_status;
    }

    public void setF_status(String f_status) {
        this.f_status = f_status;
    }

    public String getF_key() {
        return f_key;
    }

    public void setF_key(String f_key) {
        this.f_key = f_key;
    }

}
