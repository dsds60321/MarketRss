package com.gen.marketrss.common.constant;

public final class ResponseMessage {

    public static final String SUCCESS = "Success.";
    public static final String VALIDATION_FAIL = "Validation failed.";
    public static final String DUPLICATE_ID = "Duplicate Id";
    public static final String SIGN_IN_FAIL = "Login information mismatch.";
    public static final String CERTIFICATIOn_FAIL = "Certification failed.";
    public static final String DATABASE_ERROR = "Database error.";


    private ResponseMessage() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
