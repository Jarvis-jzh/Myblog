package com.jzh.myblog.dto;

import com.jzh.myblog.util.RegexpUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/3/26 17:37
 * @description 修改密码
 */
@Data
public class ModifyPasswordDTO {

    @Pattern(regexp = RegexpUtil.PHONE, message = "手机号格式错误")
    private String phone;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String code;
}
