package com.jdd.imadmin.common.redislock;

public interface Lock {
    /**
     * 上锁接口，当线程持有该所的时候，立刻返回，否则阻塞
     */
    void lock();

    /**
     * 尝试上锁，立刻返回，非阻塞，true为获得该锁，false为没有获得该锁
     */
    boolean tryLock();

    /**
     * 在timeOut的时间内尝试上锁，立刻返回，非阻塞，true为获得该锁，false为没有获得该锁
     *
     * @param timeOut
     * @return
     */
    boolean tryLock(int timeOut);

    /**
     * 释放锁。只有当锁中记录的值和该锁的值匹配的时候才可Del
     *
     */
    void unLock();
}
