package com.yiwugou.reb.web.exc;

public class LoginException extends RuntimeException {

    private static final long serialVersionUID = 7441239606114377403L;

    public LoginException(String msg) {
        super(msg);
    }

    public LoginException() {
        super();
    }

}
