package com.yiwugou.reb.api.entity.bean;

import lombok.Data;

@Data
public class UserProductBean implements BaseBean {

    private static final long serialVersionUID = 1L;

    private Long userId;
    private Long productId;
    private String productName;

}
