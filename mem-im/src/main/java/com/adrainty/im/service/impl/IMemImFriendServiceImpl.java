package com.adrainty.im.service.impl;

import com.adrainty.im.feign.SysUserClient;
import com.adrainty.im.mapper.MemImFriendMapper;
import com.adrainty.im.service.IMemImFriendService;
import com.adrainty.module.im.MemImFriend;
import com.adrainty.module.sys.SysUserDto;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 22:56
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class IMemImFriendServiceImpl extends ServiceImpl<MemImFriendMapper, MemImFriend> implements IMemImFriendService {

    private final SysUserClient sysUserClient;

    @Override
    public List<SysUserDto> searchFriend(Long userId) {
        List<Long> friends = this.baseMapper.getFriends(userId);
        if (friends.isEmpty()) return new ArrayList<>();
        return sysUserClient.userInfoBatch(friends);
    }
}
