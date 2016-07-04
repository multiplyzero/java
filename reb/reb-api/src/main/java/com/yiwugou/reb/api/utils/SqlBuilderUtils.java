package com.yiwugou.reb.api.utils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SqlBuilderUtils {
    public static class BaseDaoSqlBuilder {
        public static String countBuilder(Class<?> clazz, @Param("param") final Map<String, Object> param) {
            final String tableName = SqlBuilderUtils.tableName(clazz);
            return new SQL() {
                {
                    SELECT("count(1)").FROM(tableName).WHERE("id = #{param.clazz}");
                }
            }.toString();
        }

        public static String deleteByPrimaryKeyBuilder(Class<?> clazz, Serializable id) {
            final String tableName = SqlBuilderUtils.tableName(clazz);
            final String idName = SqlBuilderUtils.idName(clazz);
            return new SQL() {
                {
                    DELETE_FROM(tableName).WHERE(idName + " = #{1}");
                }
            }.toString();

        }

        public static String selectByPrimaryKeyBuilder(Class<?> clazz, Serializable id) {
            final String tableName = SqlBuilderUtils.tableName(clazz);
            final String idName = SqlBuilderUtils.idName(clazz);
            return new SQL() {
                {
                    SELECT(tableName).WHERE(idName + " = #{param2}");
                }
            }.toString();
        }

        public static String insertBuilder(Object entity) {
            final Class<?> clazz = entity.getClass();
            final String tableName = SqlBuilderUtils.tableName(clazz);
            String sql = new SQL() {
                {
                    INSERT_INTO(tableName);
                    for (Field field : clazz.getDeclaredFields()) {
                        String columnName = SqlBuilderUtils.columnName(field);
                        String fileName = field.getName();
                        VALUES(columnName, "#{" + fileName + "}");
                    }

                }
            }.toString();
            return sql;
        }
    }

    public static class UserSqlBuilder {
        public static String listByEmail(final String email) {
            return new SQL() {
                {
                    SELECT("id,email,password").FROM("T_MM_USER");
                    if (email != null) {
                        WHERE("email = #{email}");
                    }
                    ORDER_BY("id");
                }
            }.toString();
        }

        public static String page(final String email, final Integer currPage) {
            return new SQL() {
                {
                    SELECT("id,email,password").FROM("T_MM_USER");
                    if (email != null) {
                        WHERE("email = #{email}");
                    }
                    ORDER_BY("id");
                }
            }.toString();
        }
    }

    public static String tableName(Class<?> clazz) {
        Table table = (Table) clazz.getAnnotation(Table.class);
        if (table != null) {
            String tableName = table.name();
            if (StringUtils.isNotBlank(tableName)) {
                return tableName;
            }
        }
        return "T_" + clazz.getSimpleName().toUpperCase();
    }

    public static String idName(Class<?> clazz) {
        String idName = "id";
        for (Field field : clazz.getDeclaredFields()) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                idName = SqlBuilderUtils.columnName(field);
                break;
            }
        }
        return idName;
    }

    public static String columnName(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column != null) {
            String name = column.name();
            if (StringUtils.isNotBlank(name)) {
                return name;
            }
        }
        return NameUtils.toUnderline(field.getName());
    }

}
