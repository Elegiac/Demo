package std.demo.local.hazelcast;

import java.util.concurrent.ConcurrentMap;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;

public class Client {

	public static void main(String[] args) {
		HazelcastInstance instance1 = HazelcastClient.newHazelcastClient();
		;
		ConcurrentMap map = instance1.getMap("new Map");
	
		System.out.println(map.get("1123"));
		instance1.shutdown();
	}

}
