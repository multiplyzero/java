package com.yiwugou.reb.web.action;

import javax.servlet.http.HttpServletRequest;

import com.yiwugou.reb.api.utils.CookieUtils;
import com.yiwugou.reb.web.code.LoginUser;
import com.yiwugou.reb.web.exc.LoginException;

public class BaseAction {

    public LoginUser loginUserReturnNull(HttpServletRequest request) {
        LoginUser loginUser = (LoginUser) request.getSession().getAttribute(CookieUtils.SESSION_ID);
        return loginUser;
    }

    public LoginUser loginUser(HttpServletRequest request) {
        LoginUser loginUser = loginUserReturnNull(request);
        if (loginUser == null) {
            throw new LoginException();
        }
        return loginUser;
    }
}
