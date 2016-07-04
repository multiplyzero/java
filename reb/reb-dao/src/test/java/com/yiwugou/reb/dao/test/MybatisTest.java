package com.yiwugou.reb.dao.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.yiwugou.reb.api.utils.NameUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath*:conf/spring/reb-dao.xml" })
// @ActiveProfiles("test")
public class MybatisTest {

    @Resource
    private DataSource dataSource;

    @Resource
    private VelocityEngine velocityEngine;

    /**
     * 生成mybatis.xml文件
     */
    @Test
    public void generateXml() throws Exception {
        String templateName = "DemoTable.xml";

        String table = "t_user";
        String namespace = "com.yiwugou.reb.dao.user.UserDao";
        String resultType = "com.yiwugou.reb.api.entity.user.User";

        Connection conn = dataSource.getConnection();

        DatabaseMetaData metaData = conn.getMetaData();
        ResultSet rs = metaData.getColumns(null, "%", table, "%");

        Map<String, String> columnMap = new HashMap<String, String>();
        while (rs.next()) {
            String columnName = rs.getString("COLUMN_NAME");
            columnMap.put(columnName, NameUtils.toCamelhump(columnName));
            // String columnType = rs.getString("TYPE_NAME");
            // int datasize = rs.getInt("COLUMN_SIZE");
            // int digits = rs.getInt("DECIMAL_DIGITS");
            // int nullable = rs.getInt("NULLABLE");
            // System.err.println(columnName + "-" + columnType + "-" + datasize
            // + "-" + digits + "-" + nullable);
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("table", table);
        map.put("namespace", namespace);
        map.put("resultType", resultType);
        map.put("columnMap", columnMap);
        String str = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "UTF-8", map);
        System.err.println();
        System.err.println(str);
        System.err.println();

        conn.close();
    }
}
