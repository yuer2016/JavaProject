package com.oue.collect.utils;

import java.util.HashMap;
import java.util.Map;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisClientTest  {
	JedisPool  pool = null;
	Jedis  redisClient = null;
	
	
	public void testinit(){
		try {
		   pool = RedisClient.INSTANCE.getPool();
		   redisClient = pool.getResource();
		} catch (Exception e) {
			e.printStackTrace();
			RedisClient.INSTANCE.returnResource(redisClient);
		}
	}
	
	
	public void testRedisClient(){
		
		try {
			Map<String, String> value = new HashMap<String, String>();
			value.put("netty", "1");
			value.put("ios", "1");
			redisClient.hmset("collect:test", value);
			redisClient.hdel("collect:test", "netty");
			redisClient.hdel("collect:test", "ios");
		} catch (Exception e) {
			e.printStackTrace();
			RedisClient.INSTANCE.returnResource(redisClient);
		}finally {
			RedisClient.INSTANCE.returnResource(redisClient);
		}
	}
	

}
