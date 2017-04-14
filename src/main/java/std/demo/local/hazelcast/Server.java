package std.demo.local.hazelcast;

import java.util.Map;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class Server {
	public static void main(String[] args) {
		// https://hazelcast.org/
		Config config = new Config();
		HazelcastInstance server = Hazelcast.newHazelcastInstance(config);

		Map<String, Object> map = server.getMap("test");

		map.put("" + System.currentTimeMillis(), System.currentTimeMillis());

		// ConcurrentMap<String, String> map = h.getMap("my-distributed-map");
		// map.put("key", "value");
		// map.get("key");
		//
		// //Concurrent Map methods
		// map.putIfAbsent("somekey", "somevalue");
		// map.replace("key", "value", "newvalue");
		// h.shutdown();
	}
}
