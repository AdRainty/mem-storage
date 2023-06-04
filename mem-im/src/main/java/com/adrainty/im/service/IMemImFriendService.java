package com.adrainty.im.service;

import com.adrainty.module.im.MemImFriend;
import com.adrainty.module.sys.SysUserDto;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 22:55
 */
public interface IMemImFriendService extends IService<MemImFriend> {

    /**
     * 查找用户好友列表
     * @param userId 用户ID
     * @return 好友列表
     */
    List<SysUserDto> searchFriend(Long userId);

}
