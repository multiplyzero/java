package com.yiwugou.reb.web.action.index;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yiwugou.reb.api.entity.user.User;
import com.yiwugou.reb.api.service.user.UserService;
import com.yiwugou.reb.api.utils.AssertUtils;
import com.yiwugou.reb.api.utils.CookieUtils;
import com.yiwugou.reb.web.action.BaseAction;
import com.yiwugou.reb.web.code.LoginUser;

import net.spy.memcached.MemcachedClient;

@Controller
public class LoginAction extends BaseAction {

    @Resource
    private MemcachedClient memcachedClient;

    @Resource
    private UserService userService;

    @RequestMapping("login")
    public ModelAndView login(HttpServletRequest request, HttpServletResponse response, String username,
            String password, String redirect) {
        ModelAndView mv = new ModelAndView();
        AssertUtils.notNull(username);
        AssertUtils.notNull(password);
        User user = userService.checkLogin(username, password);
        if (user != null) {
            this.loginSuccess(response, user);
            if (StringUtils.isNotBlank(redirect)) {
                mv.setViewName("redirect:" + redirect);
            } else {
                mv.setViewName("login_success");
            }
        } else {
            mv.addObject("errorMsg", "用户名 密码不匹配");
            mv.setViewName("login_faild");
        }
        return mv;
    }

    private void loginSuccess(HttpServletResponse response, User user) {
        String sessionId = CookieUtils.generateSessionId();
        CookieUtils.setCookie(response, CookieUtils.SESSION_ID, sessionId, CookieUtils.MAX_AGE_BROWSER_OUT, true, null);

        LoginUser loginUser = new LoginUser();
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setUser(user);
        this.memcachedClient.set(sessionId, 60 * 60 * 2, loginUser);
    }
}
