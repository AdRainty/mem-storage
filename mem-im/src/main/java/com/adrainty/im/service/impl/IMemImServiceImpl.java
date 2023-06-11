package com.adrainty.im.service.impl;

import com.adrainty.im.constants.ChatTypeEnum;
import com.adrainty.im.service.IMemImFriendService;
import com.adrainty.im.service.IMemImGroupService;
import com.adrainty.im.service.IMemImMsgService;
import com.adrainty.im.service.IMemImService;
import com.adrainty.module.im.MemImGroup;
import com.adrainty.module.im.MemTalkVo;
import com.adrainty.module.sys.SysUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/11 17:45
 */

@Service
public class IMemImServiceImpl implements IMemImService {

    private final IMemImGroupService iMemImGroupService;

    private final IMemImFriendService iMemImFriendService;

    private final IMemImMsgService iMemImMsgService;

    @Autowired
    public IMemImServiceImpl(IMemImGroupService iMemImGroupService, IMemImFriendService iMemImFriendService,
                             IMemImMsgService iMemImMsgService) {
        this.iMemImGroupService = iMemImGroupService;
        this.iMemImFriendService = iMemImFriendService;
        this.iMemImMsgService = iMemImMsgService;
    }

    @Override
    public List<MemTalkVo> getTalkVo(Long userId) {
        List<MemTalkVo> groupTalkVo = this.getGroupTalkVo(userId);
        List<MemTalkVo> friendTalkVo = this.getFriendTalkVo(userId);
        friendTalkVo.addAll(groupTalkVo);
        // 根据未读消息数排序
        friendTalkVo.sort(Comparator.comparingLong(MemTalkVo::getCount));
        return friendTalkVo;
    }

    /**
     * 获取群聊消息对象
     * @param userId 用户ID
     * @return 群聊消息对象
     */
    private List<MemTalkVo> getGroupTalkVo(Long userId) {
        List<MemImGroup> memImGroups = iMemImGroupService.searchGroupTalk(userId);
        List<MemTalkVo> memTalkVos = new ArrayList<>();
        memImGroups.forEach(item -> {
            MemTalkVo memTalkVo = new MemTalkVo();
            Long count = iMemImMsgService.getGroupMsgCount(item.getId());

            memTalkVo.setName(item.getName());
            memTalkVo.setAvatar(item.getAvatar());
            memTalkVo.setChatType(ChatTypeEnum.GROUP_CHAT.getCode());
            memTalkVo.setId(item.getId());
            memTalkVo.setCount(count);
            memTalkVos.add(memTalkVo);
        });
        return memTalkVos;
    }

    /**
     * 获取单聊消息对象
     * @param userId 用户ID
     * @return 单聊消息对象
     */
    private List<MemTalkVo> getFriendTalkVo(Long userId) {
        List<SysUserDto> sysUserDtos = iMemImFriendService.searchFriend(userId);
        List<MemTalkVo> memTalkVos = new ArrayList<>();
        sysUserDtos.forEach(item -> {
            MemTalkVo memTalkVo = new MemTalkVo();
            Long count = iMemImMsgService.getSingleMsgCount(item.getId());

            memTalkVo.setName(item.getUsername());
            memTalkVo.setAvatar(item.getAvatar());
            memTalkVo.setChatType(ChatTypeEnum.SINGLE_CHAT.getCode());
            memTalkVo.setId(item.getId());
            memTalkVo.setCount(count);
            memTalkVos.add(memTalkVo);
        });
        return memTalkVos;
    }
}
