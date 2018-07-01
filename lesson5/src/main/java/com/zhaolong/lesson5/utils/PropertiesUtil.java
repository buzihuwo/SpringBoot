package com.zhaolong.lesson5.utils;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

public class PropertiesUtil {
    private final ResourceBundle resource;
    private final String fileName;

    public PropertiesUtil(String fileName){
        this.fileName=fileName;
        Locale locale=new Locale("zh","CN");
        this.resource=ResourceBundle.getBundle(this.fileName,locale);
    }

    public String getValue(String key){
        String message=this.resource.getString(key);
        return message;
    }

    public Enumeration<String> getKeys(){
        return resource.getKeys();
    }

}
