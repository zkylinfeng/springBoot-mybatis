/**
 * UserDO.java
 */
package com.common.lightweight.domain.dao;

import com.baomidou.mybatisplus.annotations.TableName;
import com.common.lightweight.core.base.BaseModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Copyright (c) BOC Services（Kunshan）Co.,Ltd.
 *
 * @author bocs
 */
@Data
@TableName("users")
public class UserDO extends BaseModel {
    private String userName;
    private String hashed_password;
    private String firstname;
    private String lastname;
    private Integer admin;
    private Integer status;
    private LocalDateTime last_login_on;
    private String language;
    private Integer auth_source_id;
    private LocalDateTime created_on;
    private LocalDateTime updated_on;
    private String type;
    private String identity_url;
    private String mail_notification;
    private String salt;
    private Integer must_change_passwd;
    private LocalDateTime passwd_changed_on;
    private String bocsType;
    private String bocsSkills;
}
