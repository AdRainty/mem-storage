package com.adrainty.im.service.impl;

import com.adrainty.common.utils.ValidatorUtils;
import com.adrainty.im.mapper.MemImGroupMapper;
import com.adrainty.im.service.IMemImGroupService;
import com.adrainty.im.service.IMemImMemberService;
import com.adrainty.module.group.AddGroup;
import com.adrainty.module.im.MemImGroup;
import com.adrainty.module.im.MemImMember;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
public class IMemImGroupServiceImpl extends ServiceImpl<MemImGroupMapper, MemImGroup> implements IMemImGroupService {

    private final IMemImMemberService iMemImMemberService;

    @Override
    public List<MemImGroup> searchGroupTalk(Long userId) {
        LambdaQueryWrapper<MemImMember> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemImMember::getMember, userId);
        List<Long> groups = iMemImMemberService.list(wrapper).stream().map(MemImMember::getCrowd).distinct().toList();
        if (groups.isEmpty()) return new ArrayList<>();
        return this.baseMapper.selectBatchIds(groups);
    }

    @Override
    public void addGroup(Long userId, MemImGroup memImGroup) {
        ValidatorUtils.validateEntity(memImGroup, AddGroup.class);
        this.baseMapper.insert(memImGroup);
        MemImMember memImMember = new MemImMember();
        memImMember.setCrowd(memImGroup.getId());
        memImMember.setMember(userId);
        iMemImMemberService.save(memImMember);
    }
}
