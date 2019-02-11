/**
 * UserTimes.java
 */
package com.common.lightweight.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Copyright (c) BOC Services（Kunshan）Co.,Ltd.
 *
 * @author bocs
 */
@Data
public class UserTimes implements Serializable {
    private static final long serialVersionUID = 7920781150861886977L;
    /**
     * 用户id
     */
    private Long user_id;
    /**
     * 用户姓
     */
    private String firstname;
    /**
     * 用户名
     */
    private String lastname;
    /**
     * 用户登记的总工时
     */
    private Double hours;
}
