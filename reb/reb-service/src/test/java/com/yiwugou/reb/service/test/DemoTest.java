package com.yiwugou.reb.service.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yiwugou.reb.api.entity.bean.UserProductBean;
import com.yiwugou.reb.api.entity.user.User;
import com.yiwugou.reb.api.service.user.UserService;
import com.yiwugou.reb.api.utils.Pager;
import com.yiwugou.reb.api.utils.ParamUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:conf/spring/reb-service.xml", "classpath*:conf/spring/reb-dao.xml" })
// @ActiveProfiles("test")
public class DemoTest {

    @Resource
    private UserService userService;

    // @Test
    public void test() throws Exception {
        User user = userService.selectByPrimaryKey(20);
        System.err.println(user);

        User user1 = userService.selectByPrimaryKey("username,create_time", 20);
        System.err.println(user1);

        List<User> list1 = userService.list("username,create_time", null);
        System.err.println(list1);

        List<User> list2 = userService.list("username,create_time as createTime", null);
        System.err.println(list2);

        int count = userService.count(null);
        System.err.println(count);

    }

    /**
     * 事务测试
     */
    @Test
    public void transaction() {
        try {
            this.userService.transactional();
        } catch (Exception e) {
            System.err.println("after:" + userService.count(null));
        }

    }

    // @Test
    public void page() {
        Pager<User> page = this.userService.page(1, null);
        System.err.println(page);

        Map<String, Object> param = new HashMap<String, Object>();
        ParamUtils.initSortAndDir(param, "id");
        param.put("username", "username1");
        param.put("password", "password1");
        Pager<User> page1 = this.userService.page("id,username,password,create_time", 1, 10, param);
        System.err.println(page1);
    }

    // @Test
    public void pageBean() {
        Map<String, Object> param = new HashMap<String, Object>();
        ParamUtils.initSortAndDir(param, "t.id desc");

        Pager<UserProductBean> page1 = this.userService.pageUserProductBean(1, param);
        System.err.println(page1);
    }

    // @Test
    public void pageMap() {
        Map<String, Object> param = new HashMap<String, Object>();
        ParamUtils.initSortAndDir(param, "t.id desc");

        Pager<Map> pageMap = this.userService.pageUserProductMap(1, param);
        System.err.println(pageMap);
    }

    // @Test
    public void insert() {
        for (int i = 1; i <= 50; i++) {
            User user = new User();
            user.setUsername("username" + i);
            user.setPassword("password" + i);
            user.setCreateTime(System.currentTimeMillis());
            Serializable s = this.userService.insert(user);
            System.err.println(s);
        }
    }

    // @Test
    public void delete() {
        System.err.println(this.userService.deleteByPrimaryKey(1));
    }

    // @Test
    public void update() {
        User user = new User();
        user.setId(6L);
        user.setUsername("username0");
        user.setPassword("password0");

        int t = this.userService.updateBySelective(user);
        System.err.println(t);
    }
}
