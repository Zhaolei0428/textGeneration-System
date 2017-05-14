package com.zhaolei.config;

/**
 * Created by zhaolei on 2017/4/18.
 */
public class RegExConfig {
    private RegExConfig(){}

    public static final String password = "[\\w]{8,16}";
    public static final String userName = "[a-zA-Z0-9_]{6,12}";
}
