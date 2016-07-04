package com.yiwugou.reb.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.yiwugou.reb.api.entity.BaseEntity;

/**
 * 
 * @author zhanxiaoyong@yiwugou.com
 * 
 * @since 2016年6月17日 下午3:25:16
 */
public interface BaseDao<T extends BaseEntity> {

    public int count(Map<String, Object> param);

    public List<T> list(Map<String, Object> param);

    public T selectByPrimaryKey(Map<String, Object> param);

    public int deleteByPrimaryKey(Serializable id);

    public void insert(T t);

    public int update(T t);

    public int updateBySelective(T t);
}
