package com.jzh.myblog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jzh.myblog.dto.FriendLinkPageDTO;
import com.jzh.myblog.dto.PageDTO;
import com.jzh.myblog.entity.Friendlink;
import com.jzh.myblog.enums.CodeEnum;
import com.jzh.myblog.mapper.FriendlinkMapper;
import com.jzh.myblog.response.Result;
import com.jzh.myblog.service.FriendlinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzh.myblog.util.ResultUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 友情链接 服务实现类
 * </p>
 *
 * @author jzh
 * @since 2020-02-10
 */
@Service
public class FriendlinkServiceImpl extends ServiceImpl<FriendlinkMapper, Friendlink> implements FriendlinkService {

    /**
     * 1、新增    2、删除    3、修改
     * @param friendLinkPageDTO
     * @return
     */
    @Override
    public Result updateFriendLink(FriendLinkPageDTO friendLinkPageDTO) {
        Friendlink friendlink = this.getBaseMapper().getFriendlinkByIdOrBlogger(friendLinkPageDTO.getId(), friendLinkPageDTO.getBlogger());
        // 新增
        if (1 == friendLinkPageDTO.getType()) {
            if (null == friendlink) {
                friendlink = new Friendlink();
                BeanUtils.copyProperties(friendLinkPageDTO, friendlink);
                return ResultUtil.success(this.save(friendlink), CodeEnum.ADD_FRIEND_LINK_EXCEPTION);
            } else {
                return ResultUtil.error(CodeEnum.FRIEND_LINK_EXIST);
            }
        } else if (2 == friendLinkPageDTO.getType()) {
            // 删除
            if (null == friendlink) {
                return ResultUtil.error(CodeEnum.FRIEND_LINK_NOT_EXIST);
            } else {
                return ResultUtil.success(this.getBaseMapper().deleteById(friendlink.getId()) == 1, CodeEnum.DELETE_FRIEND_LINK_EXCEPTION);
            }
        } else {
            // 修改，检查是否存在该博主
            if (null == friendlink) {
                return ResultUtil.error(CodeEnum.FRIEND_LINK_NOT_EXIST);
            } else {
                BeanUtils.copyProperties(friendLinkPageDTO, friendlink);
                return ResultUtil.success(this.getBaseMapper().updateById(friendlink) == 1, CodeEnum.UPDATE_FRIEND_LINK_EXCEPTION);
            }
        }
    }

    @Override
    public Result getFriendLink(PageDTO pageDTO) {
        PageHelper.startPage(pageDTO.getPageNum(), pageDTO.getRows());
        List<Friendlink> friendLink = this.getBaseMapper().getFriendLink();
        PageInfo<Friendlink> pageInfo = new PageInfo<>(friendLink);
        return ResultUtil.success(pageInfo);
    }

    @Override
    public Result<List<Friendlink>> getFriendLink() {
        List<Friendlink> friendLink = this.getBaseMapper().getFriendLink();
        return ResultUtil.success(friendLink);
    }
}
