package com.jdd.imadmin.common.logcontext;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.jdd.imadmin.common.util.ClientIpHolder;
import com.jdd.imadmin.common.util.GfJsonUtil;
import com.jdd.imadmin.common.util.IpUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Log4RedisAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private ExecutorService fixedThreadPool = new ThreadPoolExecutor(5, 10, 2000L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(500));
    private String appName;
    private String host;
    private String port;
    private String password;
    private String key;
    private JedisPool jedisPool;

    @Override
    protected void append(ILoggingEvent ev) {
        final String appNames = this.appName;
        final String host = this.host;
        final Integer port = Integer.parseInt(this.port);
        final String auth = this.password;
        final String key = this.key;
        String clientIp = ClientIpHolder.get();
        fixedThreadPool.execute(() -> {
            LogCommand lc = new LogCommand();
            lc.setClientIp(clientIp);
            String ip = IpUtils.getIp();
            String hostName = IpUtils.getHostName();
            lc.setIp(ip);
            lc.setAppName(appNames);
            StringBuffer sb = new StringBuffer();
            sb.append(ev.getFormattedMessage());
            lc.setContent(sb.toString());
            Timestamp ts = new Timestamp(ev.getTimeStamp());
            lc.setDtNow(ts);
            lc.setHostName(hostName);
            lc.setLogType(appNames);
            lc.setSite("");
            lc.setSiteID(0);
            lc.setSubject(ev.getLoggerName());
            lc.setUrl("http://");
            int levelLog = ev.getLevel().toInt();
            switch (levelLog) {
                case 10000:
                    lc.setLogLevel(LogLevel.Debug);
                    break;
                case 20000:
                    lc.setLogLevel(LogLevel.Info);
                    break;
                case 30000:
                    lc.setLogLevel(LogLevel.Warn);
                    break;
                case 40000:
                    lc.setLogLevel(LogLevel.Error);
                    break;
                case 50000:
                    lc.setLogLevel(LogLevel.Fatal);
                    break;
                default:
                    break;
            }

            this.rpushNoLog(GfJsonUtil.toJSONString(lc));
        });
    }
    private void rpushNoLog(String info){
        if(this.jedisPool == null) {
            synchronized(this) {
                this.initJedis();
            }
        }
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            jedis.rpush(this.key, new String[]{info});
        } catch (Exception var8) {
            var8.printStackTrace();
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    private void initJedis() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(8);
        config.setMaxIdle(8);
        config.setMaxWaitMillis(10000L);
        config.setTestOnBorrow(true);
        if(StringUtils.isEmpty(this.password)) {
            this.jedisPool = new JedisPool(config, this.host, Integer.parseInt(this.port), 10000);
        } else {
            this.jedisPool = new JedisPool(config, this.host, Integer.parseInt(this.port), 10000, this.password);
        }

    }


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

}
