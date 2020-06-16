package com.jdd.imadmin.common.redislock;


import com.jdd.imadmin.common.context.SpringContextHolder;
import com.jdd.imadmin.common.redis.RedisManager;

public class RedisDistributedLock implements Lock {
    private RedisManager redisManager = SpringContextHolder.getBean(RedisManager.class);

    private String lockName;
    private String lockValue;
    private long timeOut;
    /**
     * 默认超时时间，毫秒为单位
     */
    public static final long DEFAULT_TIMEOUT = 3000L;

    public RedisDistributedLock(String lockName) {
        this(lockName, DEFAULT_TIMEOUT);
    }

    public RedisDistributedLock(String lockName, long timeOut) {
        this.lockName = lockName;
        this.timeOut = timeOut;
    }

    @Override
    public void lock() {
        int count = 0;
        for (; ; ) {
            lockValue = getTime() + "";
            boolean result = redisManager.setNx(lockName, lockValue);
            if (result) {
                break;
            }
            //每20次检测一次
            if (isTimeOut() && count++ % 20 == 0) {
                redisManager.del(lockName);
            }
            try {
                Thread.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean tryLock() {
        lockValue = getTime() + "";
        boolean result = redisManager.setNx(lockName, lockValue);
        if (result) {
            return true;
        }
        if (isTimeOut()) {
            redisManager.del(lockName);
        }
        return false;
    }

    @Override
    public boolean tryLock(int timeOut) {
        boolean flag = false;
        Long time = System.currentTimeMillis();
        int count = 0;
        for (; ; ) {
            lockValue = getTime() + "";
            boolean result = redisManager.setNx(lockName, lockValue);
            if (result) {
                flag = true;
                break;
            }
            //每20次检测一次
            if (isTimeOut() && count++ % 20 == 0) {
                redisManager.del(lockName);
            }
            try {
                Thread.sleep(3L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Long now = System.currentTimeMillis();
            if ((now - time) >= timeOut) {
                break;
            }
        }
        return flag;
    }

    @Override
    public void unLock() {
        String oldValue = redisManager.get(lockName);
        if (lockValue.equals(oldValue)) {
            redisManager.del(lockName);
        }
    }

    private boolean isTimeOut() {
        String oldValue = redisManager.get(lockName);
        if (oldValue == null) {
            return false;
        }
        long now = System.currentTimeMillis();
        long useTime = now - Long.parseLong(oldValue);
        if (useTime >= timeOut) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前时间戳，如果无法做到所有的机器时间同步，
     * 可以采用一个机器提供时间服务，这里调用一个时间服务保证所有机器时钟同步
     *
     * @return 时间戳
     */
    private long getTime() {
        return System.currentTimeMillis();
    }

}
