package com.easy_way_bank.helpers;

import java.util.UUID;

public class Token {
    public static String generateToken(){
        return UUID.randomUUID().toString();
    }
}
