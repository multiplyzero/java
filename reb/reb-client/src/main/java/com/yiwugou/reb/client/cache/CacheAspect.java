package com.yiwugou.reb.client.cache;

import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.yiwugou.reb.api.utils.MD5Utils;

@Aspect
@Component("cacheAspect")
public class CacheAspect {
    @Resource
    private MemcachedClient memcachedClient;

    @Around(value = "execution(* com.yiwugou.reb.client.*.*Service.*(..)) && @annotation(ca)")
    public Object around(ProceedingJoinPoint jp, CacheAnnotation ca) throws Throwable {
        Object obj = null;

        String mkey = md5Key(ca, jp);
        try {
            obj = memcachedClient.get(mkey);
        } catch (Exception e) {
            obj = null;
            memcachedClient.delete(mkey);
        }
        if (obj == null) {
            obj = jp.proceed();
            if (obj != null) {
                memcachedClient.set(mkey, ca.exptime(), obj);
            }
        }
        return obj;
    }

    public static String md5Key(CacheAnnotation ca, ProceedingJoinPoint jp) {
        String prefix = prefix(ca, jp);
        String key = initKey(jp, prefix);
        return MD5Utils.md5(key);
    }

    public static String prefix(CacheAnnotation ca, ProceedingJoinPoint jp) {
        if (StringUtils.isBlank(ca.prefix())) {
            String methodName = jp.getSignature().getName();
            String className = jp.getTarget().getClass().getName();
            return className + "." + methodName;
        }
        return ca.prefix();
    }

    public static String initKey(ProceedingJoinPoint jp, String prefix) {
        StringBuilder sb = new StringBuilder(prefix + "(");
        Object args[] = jp.getArgs();
        if (args.length > 0) {
            for (Object arg : args) {
                if (arg == null) {
                    sb.append(null + ",");
                } else if (arg instanceof Map) {
                    TreeMap treeMap = new TreeMap();
                    treeMap.putAll((Map) arg);
                    sb.append(treeMap + ",");
                } else {
                    sb.append(arg + ",");
                }
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.append(")").toString();
    }
}