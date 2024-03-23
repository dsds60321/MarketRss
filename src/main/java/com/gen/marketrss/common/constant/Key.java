package com.gen.marketrss.common.constant;

import java.time.LocalDate;

public final class Key {
    public static final String NEWS_KEY = "NEWS_PAYLOAD_";
    public static final String KAKAO_ACC_TOKEN = "KAKAO_ACC_TOKEN";
    public static final String KAKAO_MSG_OBJ_KEY = "template_object";

    public static String getCurrentDateNewsKey(String userId) {
        LocalDate currentDate = LocalDate.now();
        return NEWS_KEY + userId + "_" + currentDate;
    }

    public static String getNewsKey(String userId) {
        return NEWS_KEY + userId + "*";
    }


    private Key() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
