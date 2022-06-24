package com.dazhi100.common.component.infrastructure;

import com.dazhi100.common.clientcache.EtagStoreManager;
import com.dazhi100.common.constant.ResultCode;
import com.dazhi100.common.constant.TimeConstant;
import com.dazhi100.common.exception.ApiException;
import com.dazhi100.common.exception.RedisException;
import com.dazhi100.common.utils.ApiAssert;
import com.dazhi100.common.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;

import static com.dazhi100.common.constant.RedisKeyCommon.REDIS_ETAG_KEY;

/**
 * expire 7 day + 随机24小时内任意秒数，
 * （每次get更新过期时间，无key创建---需前置调用get方法时、get方法创建key时均校验key的正确性，不能随意创建）
 * （update不更新时间，无key不新建），自动淘汰非热点key，根据线上占用空间实际调整
 * 直接注入使用
 *
 * @author mac
 */
@Component
@Slf4j
@ConditionalOnClass(RedisTemplate.class)
public class RedisEtagStoreManager implements EtagStoreManager {


    private RedisUtil redisUtil;

    public RedisEtagStoreManager(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    private void checkKey(String key) {
        ApiAssert.notContains("?", key, ResultCode.COMMON_PARAMS_ERROR);
    }

    @Override
    public String get(String key) {
        try {
            String redisKey = MessageFormat.format(REDIS_ETAG_KEY, key);
            checkKey(redisKey);
            String s = redisUtil.get(redisKey);
            int expire = TimeConstant.ONE_WEEK + (int) (Math.random() * TimeConstant.ONE_DAY);
            if (StringUtils.hasLength(s)) {
                redisUtil.expire(redisKey, expire);
                return s;
            }
            String uuid = UUIDUtil.getUUID();
            redisUtil.set(redisKey, uuid, expire);
            return uuid;
        } catch (RedisException e) {
            log.error("RedisEtagStoreManager get error", e);
            throw new ApiException(e.getResultCode());
        }
    }

    @Override
    public Boolean del(String key) {
        try {
            String redisKey = MessageFormat.format(REDIS_ETAG_KEY, key);
            checkKey(redisKey);
            return redisUtil.remove(redisKey);
        } catch (RedisException e) {
            log.error("RedisEtagStoreManager update error", e);
            throw new ApiException(e.getResultCode());
        }
    }

}
