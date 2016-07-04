package com.yiwugou.reb.api.utils;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class AssertUtils extends Assert {
    public static void inLength(String str, int min, int max, String msg) {
        maxLength(str, max, msg);
        minLength(str, min, msg);
    }

    public static void maxLength(String str, int max, String msg) {
        if (str != null && str.length() > max) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void minLength(String str, int min, String msg) {
        if (str != null && str.length() < min) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void equals(Object obj1, Object obj2, String msg) {
        if (obj1 == null || obj2 == null || !obj1.equals(obj2)) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notEquals(Object obj1, Object obj2, String msg) {
        if (obj1 != null && obj2 != null && obj1.equals(obj2)) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notZero(Number num, String msg) {
        if (num == null || num.doubleValue() == 0.0) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void hasText(String str, RuntimeException e) {
        if (!StringUtils.hasText(str)) {
            throw e;
        }
    }

    public static void notNull(Object obj, RuntimeException e) {
        if (obj == null) {
            throw e;
        }
    }

    public static void inObjects(Object obj, String msg, Object... objs) {
        if (obj == null) {
            throw new IllegalArgumentException(msg);
        }
        if (objs != null && objs.length > 0) {
            boolean isIn = false;
            for (Object o : objs) {
                if (obj.equals(o)) {
                    isIn = true;
                    break;
                }
            }
            if (!isIn) {
                throw new IllegalArgumentException(msg);
            }
        }
    }
}
