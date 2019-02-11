/*
 * Copyright (c) 2017 BOC Services（Kunshan）Co.,Ltd.
 */
package com.common.lightweight.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.common.lightweight.domain.dao.UserDO;

import java.util.List;

/**
 *对抽奖活动参与人相关操作
 * @author zouky
 * mybatisplus.mapper.BaseMapper实现了基本的数据库CRUD操作
 */
public interface UserMapper extends BaseMapper<UserDO> {

    /**
     * 查询开发用户
     * @return
     */
    List<UserDO> selectUserDev();

}
