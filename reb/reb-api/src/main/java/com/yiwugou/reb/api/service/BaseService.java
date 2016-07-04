package com.yiwugou.reb.api.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.yiwugou.reb.api.entity.BaseEntity;
import com.yiwugou.reb.api.utils.Pager;

public interface BaseService<T extends BaseEntity> {

    public Pager<T> page(Integer currPage, Map<String, Object> param);

    public Pager<T> page(String fieldsClause, Integer currPage, Integer pageSize, Map<String, Object> param);

    public int count(Map<String, Object> param);

    public List<T> list(Map<String, Object> param);

    public List<T> list(String fieldsClause, Map<String, Object> param);

    public T selectByPrimaryKey(String fieldsClause, Serializable id);

    public T selectByPrimaryKey(Serializable id);

    public int deleteByPrimaryKey(Serializable id);

    public T insert(T t);

    public int update(T t);

    public int updateBySelective(T t);
}
