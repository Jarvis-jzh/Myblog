package com.jzh.myblog.dto;

import lombok.Data;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/27 14:17
 * @description
 */
@Data
public class UserInfoDTO {

    private String nickname;

    private Integer gender;

    private String email;

    private String birthday;
}
