package com.gen.marketrss.common.constant;

public final class ResponseCode {

    public static final String SUCCESS = "SU";
    public static final String VALIDATION_FAIL = "VF";
    public static final String DUPLICATE_ID = "DI";
    public static final String SIGN_IN_FAIL = "SF";
    public static final String CERTIFICATION_FAIL = "CF";
    public static final String CERTIFICATION_ATTEMPT_EXCEED = "CAE";
    public static final String MAIL_FAIL = "MF";

    public static final String DATABASE_ERROR = "DBE";


    private ResponseCode() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
