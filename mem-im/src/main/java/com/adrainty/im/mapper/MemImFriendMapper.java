package com.adrainty.im.mapper;

import com.adrainty.module.im.MemImFriend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author AdRainty
 * @version V1.0.0
 * @since 2023/6/3 22:50
 */
public interface MemImFriendMapper extends BaseMapper<MemImFriend> {

    List<Long> getFriends(@Param("userId") Long userId);

}
