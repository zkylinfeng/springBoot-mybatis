/**
 * TimeEntriesService.java
 */
package com.common.lightweight.service;

import com.common.lightweight.domain.dao.UserDO;

import java.util.List;

/**
 * Copyright (c) BOC Services（Kunshan）Co.,Ltd.
 *
 * @author bocs
 */
public interface UserService {
    /**
     * 查询开发用户
     * @return
     */
    List<UserDO> selectUserDev();

}
