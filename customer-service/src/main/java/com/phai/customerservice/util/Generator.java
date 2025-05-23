package com.phai.customerservice.util;

import java.util.UUID;

public class Generator {
    public static String generateUniqueCustomerId(){
        return "CUST-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }
}
