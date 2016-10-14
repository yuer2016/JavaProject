package com.oue.collect.dao.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.oue.collect.dao.CollectRedis;
import com.oue.collect.utils.RedisClient;

import redis.clients.jedis.Jedis;

public class CollectRedisImpl implements CollectRedis {

	private static final Logger logger = LoggerFactory.getLogger(CollectRedisImpl.class);
	@Override
	public String hmset(String key, Map<String, String> hash) {
		Jedis  redisClient = null;
		String result = null;
		try {
			redisClient = RedisClient.INSTANCE.getPool().getResource();
			result = redisClient.hmset(key, hash);
		} catch (IOException e) {
			logger.error(e.toString());
			RedisClient.INSTANCE.returnResource(redisClient);
		}finally {
			RedisClient.INSTANCE.returnResource(redisClient);
		}
		return result;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		Jedis  redisClient = null;
		Map<String, String> result = new HashMap<>();
		try {
			redisClient = RedisClient.INSTANCE.getPool().getResource();
			result = redisClient.hgetAll(key);
		} catch (IOException e) {
			logger.error(e.toString());
			RedisClient.INSTANCE.returnResource(redisClient);
		}finally {
			RedisClient.INSTANCE.returnResource(redisClient);
		}
		return result;
	}

	@Override
	public String hget(String key, String field) {
		Jedis  redisClient = null;
		String result = null;
		try {
			redisClient = RedisClient.INSTANCE.getPool().getResource();
			result = redisClient.hget(key, field);
		} catch (Exception e) {
			logger.error(e.toString());
			RedisClient.INSTANCE.returnResource(redisClient);
		}finally {
			RedisClient.INSTANCE.returnResource(redisClient);
		}
		return result;
	}

}
