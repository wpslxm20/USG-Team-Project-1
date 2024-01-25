package com.example.gateway.security.api;

public abstract class ApiList {

    private static final String[] blackList = {

    };

    public static String[] getBlackList() {
        return blackList;
    }
}
