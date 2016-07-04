package com.yiwugou.reb.web.action.index;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yiwugou.reb.api.entity.user.User;
import com.yiwugou.reb.api.utils.AssertUtils;
import com.yiwugou.reb.client.cache.CacheDeleteService;
import com.yiwugou.reb.client.user.CacheUserService;
import com.yiwugou.reb.web.action.BaseAction;

@Controller
public class IndexAction extends BaseAction {
    @Resource
    private CacheUserService cacheUserService;
    @Resource
    private CacheDeleteService cacheDeleteService;

    @RequestMapping("index")
    public String index(Long id, String str) {
        AssertUtils.notNull(id, "id must not be null");
        AssertUtils.maxLength(str, 50, "");
        AssertUtils.minLength(str, 10, "");

        User user = cacheUserService.cacheSelect(50L);
        System.err.println(user);

        cacheDeleteService.delete("com.yiwugou.reb.client.user.CacheUserService.cacheSelect(50)");

        // System.err.println(1 / 0);
        return "index";
    }

    @ResponseBody
    @RequestMapping("json")
    public User json() {
        User user = cacheUserService.cacheSelect(50L);
        System.err.println(user);
        System.err.println(1 / 0);
        return user;
    }
}
