package com.nouks.devotion.constants;
/**
 * @author nouks
 *
 * @date 18 Oct 2019
 */
public final class ApiConstants {
    public static final String JSON_CONTENT_TYPE = "application/json";
    public static final String ACCOUNT_ACTIVATION_SUBJECT = "Welcome";
    public static final String VOUCHER_SUBJECT = "Payment Voucher";
    public static final String VOUCHER_REQUEST_SUBJECT = "Payment Voucher Request";
    public static final String PAYMENT_DETAILS_SUBJECT = "Payment Transaction Successful";
    public static final String GET_STARTED_SUBJECT = "Get Started";
    public static final String URGENT_NOTIFICATION_SUBJECT = "Urgent: Notification";
    public static final String NOTIFICATION_SUBJECT = "Impact LLC: Notification";
    public static final String PASSWORD_RESET_SUBJECT = "Password reset";
    public static final String ACCOUNT_ACTIVATION_FROM_ADDRESS = "no-reply@impact-academia.com";
    public static final String NO_REPLY_FROM_ADDRESS = "no-reply@impact-academia.com";
    public static final String INFO_FROM_ADDRESS = "info@impact-academia.com";
    public static final String REFERRAL_SUBJECT = "Team Referral";
    public static final String APP_NAME = "Devotion for the Churches of Christ";

    private ApiConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
