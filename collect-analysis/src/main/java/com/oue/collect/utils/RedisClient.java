package com.oue.collect.utils;

import java.io.IOException;
import java.util.Properties;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 链接池
 * @author yuer
 * */
public enum RedisClient {

	INSTANCE; 
	
	private RedisClient(){	
	}
	
	private  JedisPool pool = null; 
	/** 
     * 构建redis连接池 
     *  
     * @param ip 
     * @param port 
     * @return JedisPool 
	 * @throws IOException 
     */  
    public  JedisPool getPool() throws IOException {  
    		Properties properties = ConfigUtils.INSTANCE.initProperties();
        if (pool == null) {  
            JedisPoolConfig config = new JedisPoolConfig();  
            //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；  
            //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
            config.setMaxTotal(Integer.parseInt(properties.getProperty("redis.MaxTotal")));
            //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。  
            config.setMaxIdle(Integer.parseInt(properties.getProperty("redis.MaxIdle")));
            //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；  
            config.setMaxWaitMillis(Integer.parseInt(properties.getProperty("redis.MaxWaitMillis")));
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
            config.setTestOnBorrow(true);  
            pool = new JedisPool(config, properties.getProperty("redis.host"), Integer.parseInt(properties.getProperty("redis.port")));  
        }  
        return pool;  
    }  
    
    /** 
     * 返还到连接池 
     *  
     * @param pool  
     * @param redis 
     */  
    public  void returnResource( Jedis redis) {  
        if (redis != null) {  
        		redis.close();
        }  
    }
}
