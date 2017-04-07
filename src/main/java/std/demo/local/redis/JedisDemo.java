package std.demo.local.redis;

import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class JedisDemo {
	public static void main(String[] args) {
		Jedis jedis = new Jedis("192.168.1.5", 6379);
		 // 获取数据并输出
//	     Set<String> keys = jedis.keys("*");
//	     for(String key:keys){
//	    	 System.out.println(key);
//	     }
		jedis.close();
	}
}
