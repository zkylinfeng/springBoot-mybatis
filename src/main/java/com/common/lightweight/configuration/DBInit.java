/*
 * Copyright (c) 2017 BOC Services（Kunshan）Co.,Ltd.
 */

package com.common.lightweight.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author zouky
 */
@Component
public class DBInit {
    private static Logger logger = LoggerFactory.getLogger(DBInit.class);

    @Autowired
    private DataSource dataSource;

    @Value("${spring.datasource.url}")
    String url;

    /**
     * 初始化设置数据库, 包括建表, 建序列, 建触发器
     */
    public void generateDB() {
        logger.info("current datasource url is: {}", url);
        logger.info("数据库初始化开始...");
        //获取数据库连接
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            System.out.println("自动COMMIT: " + connection.getAutoCommit());
            logger.info("建表开始...");
            createTableUserInfo(stmt);
            logger.info("建表结束...");
        } catch (SQLException e) {
            logger.error("数据库操作失败", e);
        }
    }

    private void createTableUserInfo(Statement stmt) {
        String table_user_info = "tb_userinfo";
        String CREATE_TABLE_USER_INFO = "CREATE TABLE " + table_user_info +
                " (id             BIGINT(18) PRIMARY KEY NOT NULL," +
                "  user_id        BIGINT(18)," +
                "  userType       INT," +
                "  skillDesc      VARCHAR(256))";
        createTable(stmt, table_user_info, CREATE_TABLE_USER_INFO);
    }

    /**
     * 创建一张表
     *
     * @param stmt      {@link Statement} 数据库句柄
     * @param tableName 表名
     * @param sql       创建语句
     */
    private void createTable(Statement stmt, String tableName, String sql) {
        try {
            stmt.execute(sql);
            logger.info("表" + tableName + "创建成功");
        } catch (SQLException e) {
            if (e.getErrorCode() == 955) {
                logger.info("表" + tableName + "已存在, 无需创建");
            } else if (e.getErrorCode() == 1430) {
                logger.info("列名已存在, 无需添加");
            } else if (e.getErrorCode() == 957) {
                logger.info("目标列名已存在, 无法改名");
            } else {
                logger.error("表" + tableName + "创建失败", e);
            }
        }
    }

}
