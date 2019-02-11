/*
 * Copyright (c) 2017 BOC Services（Kunshan）Co.,Ltd.
 */
package com.common.lightweight.controller;

import com.common.lightweight.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author bocs
 */
@Controller
@RequestMapping("user/")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    /**
     * 例子
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "all/Data", method = RequestMethod.POST)
    public String findTransByCondition() {
        return "index";
    }
}
