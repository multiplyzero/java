package com.yiwugou.reb.client.cache;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yiwugou.reb.api.utils.MD5Utils;

import net.spy.memcached.MemcachedClient;

@Service
public class CacheDeleteService {
    @Resource
    private MemcachedClient memcachedClient;

    private static Logger logger = Logger.getLogger(CacheDeleteService.class);

    /**
     * 清除缓存
     * 
     * @param key
     *            包名.类名.方法名(参数1,参数2,...) example:
     *            com.yiwugou.reb.client.user.CacheUserService.cacheSelect(1)
     */
    public void delete(String key) {
        if (StringUtils.isNotBlank(key)) {
            logger.info("cache-delete:" + key);
            this.memcachedClient.delete(MD5Utils.md5(key));
        }
    }
}
