package com.adrainty.file.service.impl;

import com.adrainty.file.mapper.MemFileShareMapper;
import com.adrainty.file.service.IMemFileShareService;
import com.adrainty.module.file.MemFileShare;
import com.adrainty.module.form.FileShareForm;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/17 15:22
 */

@Service
@RequiredArgsConstructor
public class MemFileShareServiceImpl extends ServiceImpl<MemFileShareMapper, MemFileShare> implements IMemFileShareService {

    @Override
    public boolean isShare(Long id, Long userId, String code) {
        LambdaQueryWrapper<MemFileShare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemFileShare::getFileId, id);
        List<MemFileShare> memFileShares = this.baseMapper.selectList(wrapper);
        List<MemFileShare> collect = memFileShares.stream().filter(
                item -> item.getSender().equals(userId) || item.getReceiver().equals(userId) || (item.getCode() != null && StringUtils.equals(code, item.getCode()))
        ).toList();
        return !CollectionUtils.isEmpty(collect);
    }

    @Override
    public void shareFile(FileShareForm form) {
        MemFileShare memFileShare = new MemFileShare();
        memFileShare.setFileId(form.getFileId());
        memFileShare.setSender(form.getSender());
        memFileShare.setReceiver(form.getReceiver());
        this.baseMapper.insert(memFileShare);
    }
}
