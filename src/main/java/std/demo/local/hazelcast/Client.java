package std.demo.local.hazelcast;

import java.util.Map;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;

public class Client {

	public static void main(String[] args) {
		HazelcastInstance client = HazelcastClient.newHazelcastClient();

		Map<String, Object> map = client.getMap("test");

		System.out.println(map.keySet());
		client.shutdown();
	}

}
