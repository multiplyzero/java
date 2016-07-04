package com.yiwugou.reb.service.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yiwugou.reb.api.entity.bean.UserProductBean;
import com.yiwugou.reb.api.entity.user.User;
import com.yiwugou.reb.api.service.user.UserService;
import com.yiwugou.reb.api.utils.Pager;
import com.yiwugou.reb.api.utils.ParamUtils;
import com.yiwugou.reb.dao.BaseDao;
import com.yiwugou.reb.dao.user.UserDao;
import com.yiwugou.reb.service.base.BaseServiceImpl;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Resource
    private UserDao userDao;

    @Override
    public Pager<UserProductBean> pageUserProductBean(Integer currPage, Map<String, Object> param) {
        int total = this.userDao.countUserProduct(param);
        Pager<UserProductBean> pager = this.initPager(null, currPage, null, param);
        List<UserProductBean> datas = this.userDao.listUserProductBean(param);
        pager.setTotal(total);
        pager.setDatas(datas);
        return pager;
    }

    @Override
    public Pager<Map> pageUserProductMap(Integer currPage, Map<String, Object> param) {
        param = ParamUtils.initParam(param);

        int total = this.userDao.countUserProduct(param);
        Pager<Map> pager = this.initPager(null, currPage, null, param);
        List<Map> datas = this.userDao.listUserProductMap(param);
        pager.setTotal(total);
        pager.setDatas(datas);
        return pager;
    }

    /**
     * 事务测试
     */
    @Override
    @Transactional
    public void transactional() {
        System.err.println("before:" + this.count(null));
        User user1 = new User();
        user1.setUsername("ppp1");
        user1.setCreateTime(System.currentTimeMillis());
        System.err.println(this.insert(user1));

        User user2 = new User();
        user2.setUsername("ppp2");
        user1.setCreateTime(System.currentTimeMillis());
        System.err.println(this.insert(user2));

        System.err.println(1 / 0);
    }

    @Override
    public User checkLogin(String username, String password) {
        return null;
    }

}
