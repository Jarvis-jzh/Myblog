package com.jzh.myblog.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名（邮箱）
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 性别
     */
    @TableField("gender")
    private Integer gender;

    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 生日
     */
    @TableField("birthday")
    private String birthday;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 个人简介
     */
    @TableField("personal_brief")
    private String personalBrief;

    /**
     * 头像地址
     */
    @TableField("avatar_img_url")
    private String avatarImgUrl;

    /**
     * 最后登录时间
     */
    @TableField("recently_landed_time")
    private Date recentlyLandedTime;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(value = "deleted", fill = FieldFill.INSERT)
    @TableLogic
    private Integer deleted;

    private List<Role> roles;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
