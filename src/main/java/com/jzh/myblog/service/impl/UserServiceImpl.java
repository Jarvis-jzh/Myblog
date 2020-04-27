package com.jzh.myblog.service.impl;

import cn.hutool.core.codec.Base64Decoder;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzh.myblog.constant.VerifyConstant;
import com.jzh.myblog.dto.ModifyPasswordDTO;
import com.jzh.myblog.dto.UserInfoDTO;
import com.jzh.myblog.entity.User;
import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.exception.CommonException;
import com.jzh.myblog.exception.LoginException;
import com.jzh.myblog.mapper.UserMapper;
import com.jzh.myblog.redis.StringRedisServiceImpl;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.UserService;
import com.jzh.myblog.util.ResultUtil;
import com.jzh.myblog.util.TimeUtil;
import com.jzh.myblog.util.oss.AliyunOssUtil;
import com.jzh.myblog.util.oss.FolderNameEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * <p>
 * 用户实体类 服务实现类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private StringRedisServiceImpl redisService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AliyunOssUtil ossUtils;

    @Override
    public Result modifyPassword(ModifyPasswordDTO dto) {
        String key = VerifyConstant.SMS_CODE + dto.getPhone();
        if (redisService.hasKey(key)) {
            String code = String.valueOf(redisService.get(key));
            // redisService.remove(key);
            System.out.println(code);
            if (code.equals(dto.getCode())) {
                System.out.println(1);
                String password = passwordEncoder.encode(dto.getPassword());
                int count = this.getBaseMapper().updatePasswordByUsername(password, dto.getPhone());
                System.out.println(count);
                if (1 == count) {
                    System.out.println(2);
                    return ResultUtil.success();
                }
            }
        }
        System.out.println(3);
        return ResultUtil.error(CodeEnum.MODIFY_PASSWORD_FAIL);
    }

    @Override
    public Result getCurrentUser(String username) {
        if (StringUtils.isNotBlank(username)) {
            User user = this.getBaseMapper().getUserByUsername(username);
            if (null != user) {
                return ResultUtil.success(user);
            }
        }
        throw new LoginException("用户暂未登录授权，无法访问");
    }

    @Override
    public Result updateUserInfo(UserInfoDTO dto, String username) {
        if (StringUtils.isNotBlank(username)) {
            User user = this.getBaseMapper().getUserByUsername(username);
            if (null != user) {
                BeanUtils.copyProperties(dto, user);
                if (this.updateById(user)) {
                    return ResultUtil.success();
                }
            }
        }
        throw new CommonException("保存失败，请稍后重试！");
    }

    @Override
    public Result uploadHead(String img, String username) {
        //获得上传文件的后缀名
        int index = img.indexOf(";base64,");
        String strFileExtendName = "." + img.substring(11,index);
        img = img.substring(index + 8);
        byte[] bytes = Base64Decoder.decode(img);
        InputStream is = new ByteArrayInputStream(bytes);
        String url = ossUtils.upLoad(new TimeUtil().getCurrentTime() + strFileExtendName, is, FolderNameEnum.USER_HEAD_IMAGE);
        this.getBaseMapper().updateAvatarImgUrlByUsername(url, username);
        return ResultUtil.success(url);
    }
}
