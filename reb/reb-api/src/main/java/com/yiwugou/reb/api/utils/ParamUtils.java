package com.yiwugou.reb.api.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ParamUtils {
    public static final String FIELDS_CLAUSE = "fieldsClause";
    public static final String SORT_AND_DIR = "sortAndDir";
    public static final String FIRST_RESULT = "firstResult";
    public static final String PAGE_SIZE = "pageSize";
    public static final String ID = "id";

    public static void initId(Map<String, Object> param, Serializable id) {
        if (param != null && id != null) {
            param.put(ParamUtils.ID, id);
        }
    }

    public static Map<String, Object> initParam(Map<String, Object> param) {
        return param == null ? new HashMap<String, Object>(5) : param;
    }

    public static void initSortAndDir(Map<String, Object> param, String sortAndDir) {
        if (param != null && StringUtils.isNotBlank(sortAndDir)) {
            param.put(ParamUtils.SORT_AND_DIR, sortAndDir);
        }
    }

    public static void initFieldsClause(Map<String, Object> param, String fieldsClause) {
        if (param != null && StringUtils.isNotBlank(fieldsClause)) {
            param.put(FIELDS_CLAUSE, fieldsClause);
        }
    }

    public static void initLimit(Map<String, Object> param, Pager<?> pager) {
        if (param != null && pager != null) {
            param.put(ParamUtils.FIRST_RESULT, pager.getFirstResult());
            param.put(ParamUtils.PAGE_SIZE, pager.getPageSize());
        }
    }

}
