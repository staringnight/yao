package com.dazhi100.common.component.infrastructure;

import com.dazhi100.common.constant.ResultCode;
import com.dazhi100.common.exception.RedisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@ConditionalOnClass(RedisTemplate.class)
public class RedisUtil {
    static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    public RedisUtil(@Autowired RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private RedisTemplate<String, String> redisTemplate;

    public RedisTemplate<String, String> getRedisTemplate() {
        return redisTemplate;
    }

    // =============================common============================

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public Boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            logger.error("redis expire error", e);
            return null;
        }
    }

    public Boolean expire(String key, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            logger.error("redis expire error", e);
            return null;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public Long getExpire(String key) {
        try {
            return redisTemplate.getExpire(key, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("getExpire error", e);
            return null;
        }
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            logger.error("hasKey error", e);
            return null;
        }
    }

    // ============================String=============================

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public String get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("redis get error", e);
            return null;
        }
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, String value) throws RedisException {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public Boolean set(String key, String value, long time) throws RedisException {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     * @return
     */
    public long incr(String key, long delta) throws RedisException {
        try {
            return redisTemplate.opsForValue().increment(key, delta);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    public long incr(String key) throws RedisException {
        try {
            return redisTemplate.opsForValue().increment(key);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return
     */
    public Long decr(String key, long delta) throws RedisException {
        try {
            return redisTemplate.opsForValue().decrement(key, delta);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public Object hget(String key, String item) {
        try {
            return redisTemplate.opsForHash().get(key, item);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            return null;
        }
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<?, String> map) throws RedisException {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public Boolean hmset(String key, Map<String, String> map, long time) throws RedisException {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public Boolean hset(String key, String item, String value) throws RedisException {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public Boolean hset(String key, String item, String value, long time) throws RedisException {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public Long hdel(String key, String... item) throws RedisException {
        try {
            return redisTemplate.opsForHash().delete(key, item);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public Boolean hHasKey(String key, String item) {
        try {
            return redisTemplate.opsForHash().hasKey(key, item);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            return null;
        }
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public Double hIncr(String key, String item, double by) throws RedisException {
        try {
            return redisTemplate.opsForHash().increment(key, item, by);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    public Long hIncr(String key, String item, long by) throws RedisException {
        try {
            return redisTemplate.opsForHash().increment(key, item, by);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     * @return
     */
    public Double hdecr(String key, String item, double by) throws RedisException {
        try {
            return redisTemplate.opsForHash().increment(key, item, -by);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public Set<String> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public Boolean sHasKey(String key, String value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            return null;
        }
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSet(String key, String... values) throws RedisException {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public Long sSetAndTime(String key, long time, String... values) throws RedisException {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public Long sGetSetSize(String key) throws RedisException {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public Long setRemove(String key, String... values) throws RedisException {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }
    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public List<String> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            logger.error("RedisUtil", e);
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public Long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            logger.error("lGetListSize error", e);
            return null;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public String lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            logger.error("lGetIndex error", e);
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public Boolean lSet(String key, String value) throws RedisException {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            logger.error("lSet error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public Boolean lSet(String key, String value, long time) throws RedisException {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("lSet error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public Boolean lSet(String key, List<String> value) throws RedisException {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            logger.error("lSet error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public Boolean lSet(String key, List<String> value, long time) throws RedisException {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            logger.error("lSet error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public void lUpdateIndex(String key, long index, String value) throws RedisException {
        try {
            redisTemplate.opsForList().set(key, index, value);
        } catch (Exception e) {
            logger.error("lUpdateIndex error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public Long lRemove(String key, long count, String value) throws RedisException {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            logger.error("lRemove error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    public Long remove(Collection<String> keys) throws RedisException {
        try {
            return redisTemplate.delete(keys);
        } catch (Exception e) {
            logger.error("remove error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    public Boolean remove(String key) throws RedisException {
        try {
            return redisTemplate.delete(key);
        } catch (Exception e) {
            logger.error("remove error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }


    /**
     * 将有序集合放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public Boolean zSet(String key, String value) throws RedisException {
        try {
            // 获取已缓存的数据
            ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
            return zSetOperations.add(key, value, System.currentTimeMillis());
        } catch (Exception e) {
            logger.error("zSet error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 获取有序集合的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束  0 到 -1代表所有值
     */
    public List<String> zGet(String key, long start, long end) {
        try {
            Set<String> positionIds = redisTemplate.opsForZSet().reverseRange(key, start, end);
            return new ArrayList<>(positionIds);
        } catch (Exception e) {
            logger.error("zGet error", e);
            return null;
        }
    }

    public boolean zRangeAdd(String key, String value, double score) throws RedisException {
        try {
            return redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            logger.error("zRangeAddStr error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }


    public Long zRangeAdd(String key, Set<ZSetOperations.TypedTuple<String>> value) throws RedisException {
        try {
            return redisTemplate.opsForZSet().add(key, value);
        } catch (Exception e) {
            logger.error("zRangeAdd error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    public Double zIncrementScore(String key, String value, double score) {
        try {
            return redisTemplate.opsForZSet().incrementScore(key, value, score);
        } catch (Exception e) {
            logger.error("zIncrementScore error", e);
            return null;
        }
    }

    public Double zScore(String key, String value) {
        try {
            return redisTemplate.opsForZSet().score(key, value);
        } catch (Exception e) {
            logger.error("zScore error", e);
            return null;
        }

    }

    public Set<ZSetOperations.TypedTuple<String>> zReverseRangeByScoreWithScores(String key, double start, double end) {
        try {
            return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, start, end);
        } catch (Exception e) {
            logger.error("zReverseRangeByScoreWithScores error", e);
            return null;
        }
    }

    public Set<ZSetOperations.TypedTuple<String>> zRangeByScoreWithScores(String key, double start, double end) {
        try {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key, start, end);
        } catch (Exception e) {
            logger.error("zRangeByScoreWithScores error", e);
            return null;
        }
    }

    public Long zReverseRank(String key, String o) throws RedisException {
        try {
            return redisTemplate.opsForZSet().reverseRank(key, o);
        } catch (Exception e) {
            logger.error("zReverseRank error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 获取有序集合的内容
     *
     * @param key   键
     * @param start 开始
     * @param max   结束  0 到 -1代表所有值
     */
    public Set<String> zRange(String key, Long start, Long max) {
        try {
            return redisTemplate.opsForZSet().range(key, start, max);
        } catch (Exception e) {
            logger.error("zRange error", e);
            return null;
        }
    }

    public Set<String> zRangeScore(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().rangeByScore(key, min, max);
        } catch (Exception e) {
            logger.error("zRangeScore error", e);
            return null;
        }
    }


    /**
     * 有序集合取排名多少位
     */
    public Set<String> zReverseRange(String key, Long start, Long end) {
        try {
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
        } catch (Exception e) {
            logger.error("zReverseRange error", e);
            return null;
        }
    }

    /**
     * Count number of elements within sorted set with scores between {@code start} and {@code end}.
     */
    public Long zCount(String key, Long start, Long end) {
        try {
            return redisTemplate.opsForZSet().count(key, start, end);
        } catch (Exception e) {
            logger.error("zCount error", e);
            return null;
        }
    }

    /**
     * 删除set集合中的对象
     */
    public long zRemove(String key, String value) throws RedisException {
        try {
            return redisTemplate.opsForZSet().remove(key, value);
        } catch (Exception e) {
            logger.error("zRemove error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 删除set集合中的对象
     */
    public Long zRemoveRange(String key, Long start, Long end) throws RedisException {
        try {
            return redisTemplate.opsForZSet().removeRange(key, start, end);
        } catch (Exception e) {
            logger.error("zRemoveRangeByScore error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * Remove elements with scores between {@code start} and {@code end} from sorted set with {@code key}.
     */
    public Long zRemoveRangeByScore(String key, double start, double end) throws RedisException {
        try {
            return redisTemplate.opsForZSet().removeRangeByScore(key, start, end);
        } catch (Exception e) {
            logger.error("zRemoveRangeByScore error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 设置bitmap
     *
     * @param key     键
     * @param offset  偏移量
     * @param b       状态值，true表示记录，false表示取消记录
     * @param timeout 单位秒
     * @return
     */
    public Boolean setBit(String key, Long offset, Boolean b, long timeout) throws RedisException {
        try {
            if (redisTemplate.opsForValue().setBit(key, offset, b)) {
                return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
            }
            return false;
        } catch (Exception e) {
            logger.error("setBit error", e);
            throw new RedisException(ResultCode.REDIS_ERROR);
        }
    }

    /**
     * 获取bitmap值
     *
     * @param key    键
     * @param offset 偏移量
     * @return
     */
    public Boolean getBit(String key, Long offset) {
        try {
            return redisTemplate.opsForValue().getBit(key, offset);
        } catch (Exception e) {
            logger.error("getBit error", e);
            return null;
        }
    }

}
