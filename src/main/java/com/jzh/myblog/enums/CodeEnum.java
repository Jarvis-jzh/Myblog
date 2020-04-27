package com.jzh.myblog.enums;

/**
 * @author jzh
 * @version 1.0.0
 * @date 2020/2/4 17:30
 * @description 状态返回码
 */
public enum CodeEnum {

    /**
     * 状态码
     */
    SUCCESS_STATUS(200, "成功"),

    /**
     * 未授权及特殊问题
     */
    REQUEST_PARAMETER_ERROR(400, "请求参数有误"),
    NO_AUTHORIZATION(401, "当前未授权，无法访问"),
    GET_SET_FAIL(450, "获取&&插入异常"),
    FAIL(500, "系统发生异常，请稍后重试"),
    LOGIN_FAIL(501, "授权过期，请重新登录"),
    LOGIN_NOT_YET_OPEN(601, "登录功能暂未开放"),
    REPEAT_SUBMIT(602, "亲，请不要重复提交数据"),

    /**
     * 登录相关
     */
    USER_NOT_LOGIN(1101, "用户未登录"),
    PERMISSION_VERIFY_FAIL(1102, "权限验证失败"),
    SERVER_EXCEPTION(1103, "服务器异常"),
    MODIFY_PASSWORD_FAIL(1104, "密码修改失败"),
    USERNAME_OR_PASSWORD_WRONG(1105, "用户名或者密码错误"),
    VALIDATE_CODE_FAIL(1106, "验证码不正确"),

    /**
     * 文章相关
     */
    DELETE_ARTICLE_FAIL(1201, "删除文章失败"),
    READ_ARTICLE_THUMBS_UP_FAIL(1202, "阅读文章点赞信息失败"),
    ARTICLE_HAS_THUMBS_UP(1203, "文章已经点过赞了"),
    ARTICLE_NOT_EXIST(1204, "文章不存在"),
    PUBLISH_ARTICLE_NO_PERMISSION(1205, "发表文章没有权限"),
    PUBLISH_ARTICLE_EXCEPTION(1206, "服务器异常"),
    PUBLISH_ARTICLE_FAIL(1207, "发布文章失败"),

    /**
     * 标签相关
     */
    FIND_TAGS_CLOUD(1301, "获得所有标签成功"),
    FIND_ARTICLE_BY_TAG(1302, "通过标签获得文章成功"),

    /**
     * 分类相关
     */
    ADD_CATEGORY_SUCCESS(1401, "添加分类成功"),
    CATEGORY_EXIST(1402, "分类已存在"),
    DELETE_CATEGORY_SUCCESS(1403, "删除分类成功"),
    CATEGORY_NOT_EXIST(1404, "分类不存在"),
    CATEGORY_HAS_ARTICLE(1405, "分类下存在文章，删除失败"),
    CATEGORY_FAIL(1406, "分类操作失败"),

    /**
     * 用户相关
     */
    USERNAME_TOO_LANG(1501, "用户名太长"),
    USERNAME_BLANK(1502, "用户名为空"),
    HAS_MODIFY_USERNAME(1503, "修改个人信息成功，并且修改了用户名"),
    NOT_MODIFY_USERNAME(1504, "修改个人信息成功，但没有修改用户名"),
    USERNAME_EXIST(1505, "用户名已存在"),
    MODIFY_HEAD_PORTRAIT_FAIL(1506, "修改头像失败"),
    READ_MESSAGE_FAIL(1507, "已读信息失败"),
    USERNAME_NOT_EXIST(1508, "用户名不存在"),
    USERNAME_FORMAT_ERROR(1509, "用户名长度过长或格式不正确"),

    /**
     * 友链相关
     */
    ADD_FRIEND_LINK_SUCCESS(1601, "添加友链成功"),
    FRIEND_LINK_EXIST(1602, "友链已存在"),
    FRIEND_LINK_NOT_EXIST(1603, "友链不存在"),
    UPDATE_FRIEND_LINK_SUCCESS(1604, "更新友链成功"),
    DELETE_FRIEND_LINK_SUCCESS(1605, "删除友链成功"),
    ADD_FRIEND_LINK_EXCEPTION(1606, "增加友链异常"),
    UPDATE_FRIEND_LINK_EXCEPTION(1607, "更新友链异常"),
    DELETE_FRIEND_LINK_EXCEPTION(1608, "删除友链异常"),

    /**
     * 募捐相关
     */
    ADD_REWARD_SUCCESS(1701, "增加募捐记录成功"),
    DELETE_REWARD_SUCCESS(1702, "删除募捐记录成功"),
    ADD_REWARD_EXCEPTION(1703, "增加募捐记录异常"),
    DELETE_REWARD_EXCEPTION(1704, "删除募捐记录异常"),

    /**
     * 其它
     */
    COMMENT_BLANK(1801, "内容为空"),
    MESSAGE_HAS_THUMBS_UP(1802, "已经点过赞了"),

    /**
     * 验证相关
     */
    PHONE_ERROR(1901, "手机号错误"),
    AUTH_CODE_ERROR(1902, "验证码错误"),
    PHONE_EXIST(1903, "手机号已存在"),
    EMAIL_ERROR(1904, "邮箱格式错误"),
    EMAIL_EXIST(1905, "邮箱已存在"),
    VERIFY_CODE_FAIL(1906, "验证码有误"),
    VERIFY_CODE_IMAGE_FAIL(1907, "验证码生成失败"),

    /**
     * 文件相关
     */
    IMG_UPLOAD_FAIL(2001, "图片上传失败"),
    IMG_UPLOAD_SUCCESS(2002, "图片上传成功"),
    IMG_UPLOAD_FORMAT_ERROR(2003, "图片上传格式错误"),

    /**
     * 反馈相关
     */
    FEEDBACK_ADD_FAIL(2101, "反馈提交失败，请稍后重试"),
    FEEDBACK_ADD_SUCCESS(2102, "反馈成功，夏末会尽快处理哒")
    ;

    private int code;

    private String message;

    CodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
