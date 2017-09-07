package std.demo.local.rocketmq;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;

public class Demo {

	public static void main(String[] args) throws MQClientException {
		
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(
				"ConsumerGroupName");
		consumer.setNamesrvAddr("192.168.230.128:9876");
		consumer.setInstanceName("Consumber");
		

		
		
		
		DefaultMQProducer producer = new DefaultMQProducer("ProducerGroupName");
		producer.setNamesrvAddr("127.0.0.1:9876");
		producer.setInstanceName("Producer");
		
		producer.start();
		
		
		
		
		
		

	}

}
