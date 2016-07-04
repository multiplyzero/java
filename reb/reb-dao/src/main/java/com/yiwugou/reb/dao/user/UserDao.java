package com.yiwugou.reb.dao.user;

import java.util.List;
import java.util.Map;

import com.yiwugou.reb.api.entity.bean.UserProductBean;
import com.yiwugou.reb.api.entity.user.User;
import com.yiwugou.reb.dao.BaseDao;

public interface UserDao extends BaseDao<User> {

    public List<UserProductBean> listUserProductBean(Map<String, Object> param);

    public Integer countUserProduct(Map<String, Object> param);

    public List<Map> listUserProductMap(Map<String, Object> param);

}
