package com.yiwugou.reb.api.entity.user;

import com.yiwugou.reb.api.entity.BaseEntity;

import lombok.Data;

/**
 * 
 * @author zhanxiaoyong@yiwugou.com
 * 
 * @since 2016年6月2日 下午2:07:00
 * 
 * 
 */
@Data
public class User implements BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long createTime;

    private String username;

    private String password;

}
