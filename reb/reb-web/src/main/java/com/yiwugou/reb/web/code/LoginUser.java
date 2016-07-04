package com.yiwugou.reb.web.code;

import java.io.Serializable;

import com.yiwugou.reb.api.entity.user.User;

import lombok.Data;

@Data
public class LoginUser implements Serializable {
    private static final long serialVersionUID = -375117896476040784L;

    private User user;
    private Long loginTime;

}
