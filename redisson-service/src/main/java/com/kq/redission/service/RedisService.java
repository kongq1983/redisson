package com.kq.redission.service;

public interface RedisService {

    public void setStringValue(String key,String value);

    public void setStringValueTimeout(String key,String value,int timeout);

    public String getValue(String key);

}
