package com.gen.marketrss.common.constant;

public final class ResponseMessage {

    public static final String SUCCESS = "Success.";
    public static final String VALIDATION_FAIL = "Validation failed.";
    public static final String DUPLICATE_ID = "Duplicate Id";
    public static final String SIGN_IN_FAIL = "Login information mismatch.";
    public static final String CERTIFICATION_FAIL = "Certification failed.";
    public static final String CERTIFICATION_ATTEMPT_EXCEED = "Failed beyond the limit of number of attempts";
    public static final String MAIL_FAIL = "Mail send failed;";
    public static final String TOKEN_EXPIRED = "Token has expired.";
    public static final String UN_AUTHORIZED = "UnAuthorized Token.";
    public static final String DATABASE_ERROR = "Database error.";


    private ResponseMessage() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
