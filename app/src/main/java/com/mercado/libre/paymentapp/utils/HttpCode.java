package com.mercado.libre.paymentapp.utils;

public class HttpCode {
    /********************************************************************************
     * HTTP STATUS CODES                                                            *
     ********************************************************************************/
    // Information
    public static final int CODE_100_INFORMATION = 100; // Continue
    public static final int CODE_101_INFORMATION = 101; // Switching Protocols
    public static final int CODE_103_INFORMATION = 103; // Checkpoint

    // Successful
    public static final int CODE_200_SUCCESSFUL = 200; // OK
    public static final int CODE_201_SUCCESSFUL = 201; // Created
    public static final int CODE_202_SUCCESSFUL = 202; // Accepted
    public static final int CODE_203_SUCCESSFUL = 203; // Non-Authoritative Information
    public static final int CODE_204_SUCCESSFUL = 204; // No Content
    public static final int CODE_205_SUCCESSFUL = 205; // Reset Content
    public static final int CODE_206_SUCCESSFUL = 205; // Partial Content

    // Redirection
    public static final int CODE_300_REDIRECTION = 300; // Multiple Choices
    public static final int CODE_301_REDIRECTION = 301; // Moved Permanently
    public static final int CODE_302_REDIRECTION = 302; // Found
    public static final int CODE_303_REDIRECTION = 303; // See Other
    public static final int CODE_304_REDIRECTION = 304; // Not Modified
    public static final int CODE_306_REDIRECTION = 306; // Switch Proxy
    public static final int CODE_307_REDIRECTION = 307; // Temporary Redirect
    public static final int CODE_308_REDIRECTION = 308; // Resume Incomplete

    // Client Error
    public static final int CODE_400_CLIENT_ERROR = 400; // Bad Request
    public static final int CODE_401_CLIENT_ERROR = 401; // Unauthorized
    public static final int CODE_402_CLIENT_ERROR = 402; // Payment Required
    public static final int CODE_403_CLIENT_ERROR = 403; // Forbidden
    public static final int CODE_404_CLIENT_ERROR = 404; // Not Found
    public static final int CODE_405_CLIENT_ERROR = 405; // Method Not Allowed
    public static final int CODE_406_CLIENT_ERROR = 406; // Not Acceptable
    public static final int CODE_407_CLIENT_ERROR = 407; // Proxy Authentication Required
    public static final int CODE_408_CLIENT_ERROR = 408; // RequestTicket Timeout
    public static final int CODE_409_CLIENT_ERROR = 409; // Conflict
    public static final int CODE_410_CLIENT_ERROR = 410; // Gone
    public static final int CODE_411_CLIENT_ERROR = 411; // Length Required
    public static final int CODE_412_CLIENT_ERROR = 412; // Precondition Failed
    public static final int CODE_413_CLIENT_ERROR = 413; // RequestTicket Entity Too Large
    public static final int CODE_414_CLIENT_ERROR = 414; // RequestTicket-URI Too Long
    public static final int CODE_415_CLIENT_ERROR = 415; // Unsupported Media Type
    public static final int CODE_416_CLIENT_ERROR = 416; // Requested Range Not Satisfiable
    public static final int CODE_417_CLIENT_ERROR = 417; // Expectation Failed

    //Server Error
    public static final int CODE_500_SERVER_ERROR = 500; // Internal Server Error
    public static final int CODE_501_SERVER_ERROR = 501; // Not Implemented
    public static final int CODE_502_SERVER_ERROR = 502; // Bad Gateway
    public static final int CODE_503_SERVER_ERROR = 503; // Service Unavailable
    public static final int CODE_504_SERVER_ERROR = 504; // Gateway Timeout
    public static final int CODE_505_SERVER_ERROR = 505; // HTTP Version Not Supported
    public static final int CODE_511_SERVER_ERROR = 511; // Network Authentication Required


}
