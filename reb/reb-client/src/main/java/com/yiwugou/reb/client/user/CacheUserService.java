package com.yiwugou.reb.client.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yiwugou.reb.api.entity.user.User;
import com.yiwugou.reb.api.service.user.UserService;

@Service
public class CacheUserService {
    @Resource
    private UserService userService;

    // @CacheAnnotation
    public User cacheSelect(Long id) {
        return this.userService.selectByPrimaryKey(id);
    }

}
