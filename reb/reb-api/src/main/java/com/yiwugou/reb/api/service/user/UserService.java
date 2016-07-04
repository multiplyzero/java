package com.yiwugou.reb.api.service.user;

import java.util.Map;

import com.yiwugou.reb.api.entity.bean.UserProductBean;
import com.yiwugou.reb.api.entity.user.User;
import com.yiwugou.reb.api.service.BaseService;
import com.yiwugou.reb.api.utils.Pager;

public interface UserService extends BaseService<User> {

    Pager<UserProductBean> pageUserProductBean(Integer currPage, Map<String, Object> param);

    Pager<Map> pageUserProductMap(Integer currPage, Map<String, Object> param);

    void transactional();

    User checkLogin(String username,String password);

}
