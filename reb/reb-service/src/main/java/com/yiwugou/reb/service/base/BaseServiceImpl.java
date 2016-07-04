package com.yiwugou.reb.service.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.yiwugou.reb.api.entity.BaseEntity;
import com.yiwugou.reb.api.utils.Pager;
import com.yiwugou.reb.api.utils.ParamUtils;
import com.yiwugou.reb.dao.BaseDao;

public class BaseServiceImpl<T extends BaseEntity> {

    @Autowired
    public BaseDao<T> baseDao;

    protected Pager initPager(String fieldsClause, Integer currPage, Integer pageSize, Map<String, Object> param) {
        Pager pager = new Pager(currPage, pageSize);
        ParamUtils.initFieldsClause(param, fieldsClause);
        ParamUtils.initLimit(param, pager);
        return pager;
    }

    public Pager<T> page(Integer currPage, Map<String, Object> param) {
        return this.page(null, currPage, Pager.DEFAULT_PAGE_SIZE, param);
    }

    public Pager<T> page(String fieldsClause, Integer currPage, Integer pageSize, Map<String, Object> param) {
        param = ParamUtils.initParam(param);

        int total = this.count(param);
        Pager<T> pager = this.initPager(fieldsClause, currPage, pageSize, param);

        List<T> datas = this.list(param);
        pager.setDatas(datas);
        pager.setTotal(total);

        return pager;
    }

    public int count(Map<String, Object> param) {
        return this.baseDao.count(param);
    }

    public List<T> list(Map<String, Object> param) {
        return this.baseDao.list(param);
    }

    public List<T> list(String fieldsClause, Map<String, Object> param) {
        ParamUtils.initFieldsClause(param, fieldsClause);
        return this.baseDao.list(param);
    }

    public T selectByPrimaryKey(String fieldsClause, Serializable id) {
        Assert.notNull(id);
        Map<String, Object> param = new HashMap<>();
        ParamUtils.initFieldsClause(param, fieldsClause);
        ParamUtils.initId(param, id);
        return this.baseDao.selectByPrimaryKey(param);
    }

    public T selectByPrimaryKey(Serializable id) {
        return this.selectByPrimaryKey(null, id);
    }

    public int deleteByPrimaryKey(Serializable id) {
        Assert.notNull(id);
        return this.baseDao.deleteByPrimaryKey(id);
    }

    public T insert(T t) {
        Assert.notNull(t);
        this.baseDao.insert(t);
        return t;
    }

    public int update(T t) {
        Assert.notNull(t);
        return this.baseDao.update(t);
    }

    public int updateBySelective(T t) {
        Assert.notNull(t);
        return this.baseDao.updateBySelective(t);
    }
}
