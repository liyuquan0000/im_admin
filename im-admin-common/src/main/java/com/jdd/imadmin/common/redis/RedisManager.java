package com.jdd.imadmin.common.redis;

import com.jdd.imadmin.common.util.LogExceptionStackTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/************************************************************
 * @Description:
 * @Author: jiazhen
 ************************************************************/

@Component
@ConditionalOnBean(RedisTemplate.class)
public class RedisManager {

    private static final Logger logger = LoggerFactory.getLogger(RedisManager.class);

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Boolean expire(String key, long expireTime) {
        Long start = System.currentTimeMillis();
        try {
            Boolean success = redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：expire key:{} execution time:{}ms", key, time);
            }

            return success;
        } catch (Exception e) {
            logger.error("command：expire key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Long getExpire(String key) {
        Long start = System.currentTimeMillis();
        try {
            Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：getExpire key:{} execution time:{}ms", key, time);
            }

            return expire;
        } catch (Exception e) {
            logger.error("command：getExpire key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Boolean exists(String key) {
        Long start = System.currentTimeMillis();
        try {
            Boolean exist = redisTemplate.hasKey(key);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：exists key:{} execution time:{}ms", key, time);
            }

            return exist;
        } catch (Exception e) {
            logger.error("command：exists key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Long del(String... key) {
        try {
            if (key != null && key.length > 0) {
                Long start = System.currentTimeMillis();
                Long count = redisTemplate.delete(CollectionUtils.arrayToList(key));

                Long end = System.currentTimeMillis();
                Long time = end - start;
                if (time > 500L) {
                    logger.warn("command：del key:{} execution time:{}ms", key, time);
                }

                return count;
            }
        } catch (Exception e) {
            logger.error("command：del key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
        }
        return null;
    }

    // ============================String=============================

    public String get(String key) {
        Long start = System.currentTimeMillis();
        try {
            String obj = redisTemplate.opsForValue().get(key);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：get key:{} execution time:{}ms", key, time);
            }

            return obj;
        } catch (Exception e) {
            logger.error("command：get key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Boolean set(String key, String value) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForValue().set(key, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：set key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：set key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    public Boolean setNx(String key, String value) {
        try {
            return redisTemplate.opsForValue().setIfAbsent(key, value);
        } catch (Exception e) {
            logger.error("command：set key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    public Boolean set(String key, String value, long expireTime) {
        Long start = System.currentTimeMillis();
        try {
            if (expireTime > 0) {
                redisTemplate.opsForValue().set(key, value, expireTime, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：set key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：set key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    public Long incr(String key, long delta) {
        Long start = System.currentTimeMillis();
        try {
            Long increment = redisTemplate.opsForValue().increment(key, delta);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：incr key:{} execution time:{}ms", key, time);
            }

            return increment;
        } catch (Exception e) {
            logger.error("command：incr key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Long decr(String key, long delta) {
        Long start = System.currentTimeMillis();
        try {
            Long increment = redisTemplate.opsForValue().increment(key, -delta);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：decr key:{} execution time:{}ms", key, time);
            }

            return increment;
        } catch (Exception e) {
            logger.error("command：decr key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    // ================================Map=================================

    public Object hget(String key, String item) {
        Long start = System.currentTimeMillis();
        try {
            Object obj = redisTemplate.opsForHash().get(key, item);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hget key:{} execution time:{}ms", key, time);
            }

            return obj;
        } catch (Exception e) {
            logger.error("command：hget key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Map<Object, Object> hmget(String key) {
        Long start = System.currentTimeMillis();
        try {
            Map entries = redisTemplate.opsForHash().entries(key);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hmget key:{} execution time:{}ms", key, time);
            }

            return entries;
        } catch (Exception e) {
            logger.error("command：hmget key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public boolean hmset(String key, Map<String, Object> map) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForHash().putAll(key, map);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hmset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：hmset key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    public Boolean hmset(String key, Map<String, Object> map, long expireTime) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hmset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：hmset key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    public Boolean hset(String key, String item, Object value) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForHash().put(key, item, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：hset key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    public Boolean hset(String key, String item, Object value, long expireTime) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：hset key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    public void hdel(String key, Object... item) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForHash().delete(key, item);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hdel key:{} execution time:{}ms", key, time);
            }

        } catch (Exception e) {
            logger.error("command：hdel key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
        }
    }

    public Boolean hexists(String key, String item) {
        Long start = System.currentTimeMillis();
        try {
            Boolean exist = redisTemplate.opsForHash().hasKey(key, item);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hexists key:{} execution time:{}ms", key, time);
            }

            return exist;
        } catch (Exception e) {
            logger.error("command：hexists key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Double hincr(String key, String item, double by) {
        Long start = System.currentTimeMillis();
        try {
            Double increment = redisTemplate.opsForHash().increment(key, item, by);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hincr key:{} execution time:{}ms", key, time);
            }

            return increment;
        } catch (Exception e) {
            logger.error("command：hincr key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Double hdecr(String key, String item, double by) {
        Long start = System.currentTimeMillis();
        try {
            Double increment = redisTemplate.opsForHash().increment(key, item, -by);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：hdecr key:{} execution time:{}ms", key, time);
            }

            return increment;
        } catch (Exception e) {
            logger.error("command：hdecr key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    // ============================set=============================

    public Set<Object> smembers(String key) {
        Long start = System.currentTimeMillis();
        try {
            Set members = redisTemplate.opsForSet().members(key);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：smembers key:{} execution time:{}ms", key, time);
            }

            return members;
        } catch (Exception e) {
            logger.error("command：smembers key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Boolean sismember(String key, Object value) {
        Long start = System.currentTimeMillis();
        try {
            Boolean exist = redisTemplate.opsForSet().isMember(key, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：sismember key:{} execution time:{}ms", key, time);
            }

            return exist;
        } catch (Exception e) {
            logger.error("command：sismember key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Long sadd(String key, String... values) {
        Long start = System.currentTimeMillis();
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：sadd key:{} execution time:{}ms", key, time);
            }
            return count;
        } catch (Exception e) {
            logger.error("command：sadd key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Long sadd(String key, long expireTime, String... values) {
        Long start = System.currentTimeMillis();
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：sadd key:{} execution time:{}ms", key, time);
            }

            return count;
        } catch (Exception e) {
            logger.error("command：sadd key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Long scard(String key) {
        Long start = System.currentTimeMillis();
        try {
            Long size = redisTemplate.opsForSet().size(key);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：scard key:{} execution time:{}ms", key, time);
            }

            return size;
        } catch (Exception e) {
            logger.error("command：scard key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Long sremove(String key, Object... values) {
        Long start = System.currentTimeMillis();
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：sremove key:{} execution time:{}ms", key, time);
            }

            return count;
        } catch (Exception e) {
            logger.error("command：sremove key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }
    // ===============================list=================================

    public List<Object> lrange(String key, long startIndex, long endIndex) {
        Long start = System.currentTimeMillis();
        try {
            List list = redisTemplate.opsForList().range(key, startIndex, endIndex);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lrange key:{} execution time:{}ms", key, time);
            }

            return list;
        } catch (Exception e) {
            logger.error("command：lrange key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Long llen(String key) {
        Long start = System.currentTimeMillis();
        try {
            Long size = redisTemplate.opsForList().size(key);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：llen key:{} execution time:{}ms", key, time);
            }

            return size;
        } catch (Exception e) {
            logger.error("command：llen key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Object lindex(String key, long index) {
        Long start = System.currentTimeMillis();
        try {
            Object obj = redisTemplate.opsForList().index(key, index);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lindex key:{} execution time:{}ms", key, time);
            }

            return obj;
        } catch (Exception e) {
            logger.error("command：lindex key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    public Boolean lset(String key, String value) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForList().rightPush(key, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：lset key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    public Boolean lset(String key, String value, long expireTime) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：lset key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    public boolean lset(String key, List<String> value) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：lset key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    public Boolean lset(String key, List<String> value, long expireTime) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (expireTime > 0) {
                expire(key, expireTime);
            }
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：lset key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    public Boolean lset(String key, long index, String value) {
        Long start = System.currentTimeMillis();
        try {
            redisTemplate.opsForList().set(key, index, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lset key:{} execution time:{}ms", key, time);
            }

            return true;
        } catch (Exception e) {
            logger.error("command：lset key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

    public Long lremove(String key, long count, String value) {
        Long start = System.currentTimeMillis();
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：lremove key:{} execution time:{}ms", key, time);
            }

            return remove;
        } catch (Exception e) {
            logger.error("command：lremove key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return null;
        }
    }

    // ===============================zset=================================

    public Boolean zadd(String key, String value, Double score) {
        Long start = System.currentTimeMillis();

        try {
            Boolean success = redisTemplate.opsForZSet().add(key, value, score);
            Long end = System.currentTimeMillis();
            Long time = end - start;
            if (time > 500L) {
                logger.warn("command：zadd key:{} execution time:{}ms", key, time);
            }

            return success;
        } catch (Exception e) {
            logger.error("command：zadd key:{} ex={}", key, LogExceptionStackTrace.erroStackTrace(e));
            return false;
        }
    }

}
