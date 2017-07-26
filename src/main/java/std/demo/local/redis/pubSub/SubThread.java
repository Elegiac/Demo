package std.demo.local.redis.pubSub;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/** 由于Jedis的subscribe操作是阻塞的，因此，我们另起一个线程来进行subscribe操作。 */
public class SubThread extends Thread {
	private final JedisPool jedisPool;
	private final Subscriber subscriber = new Subscriber();

	private final String channel = "mychannel";

	public SubThread(JedisPool jedisPool) {
		super("SubThread");
		this.jedisPool = jedisPool;
	}

	@Override
	public void run() {
		System.out.println(String.format("subscribe redis, channel %s, thread will be blocked", channel));
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.subscribe(subscriber, channel);
		} catch (Exception e) {
			System.out.println(String.format("subsrcibe channel error, %s", e));
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}
