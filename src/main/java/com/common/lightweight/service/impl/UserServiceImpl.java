/**
 * TimeEntriesService.java
 */
package com.common.lightweight.service.impl;

import com.common.lightweight.mapper.UserMapper;
import com.common.lightweight.domain.dao.UserDO;
import com.common.lightweight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (c) BOC Services（Kunshan）Co.,Ltd.
 *
 * @author bocs
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    /**
     * 查询开发用户
     * @return
     */
    @Override
    public List<UserDO> selectUserDev() {
        return userMapper.selectUserDev();
    }
}
