package std.demo.local.redis.pubSub;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/** jedis实现pub/sub */
public class PubSubDemo {
	public static void main(String[] args) {
		String redisIp = "192.168.1.5";
		int reidsPort = 6379;

		JedisPoolConfig config = new JedisPoolConfig();

		JedisPool jedisPool = new JedisPool(config, redisIp, reidsPort);
		System.out.println(String.format("redis pool is starting, redis ip %s, redis port %d", redisIp, reidsPort));

		SubThread subThread = new SubThread(jedisPool);
		subThread.start();

		Publisher publisher = new Publisher(jedisPool);
		publisher.start();
	}
}
