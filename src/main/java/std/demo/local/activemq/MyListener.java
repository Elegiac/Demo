package std.demo.local.activemq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		System.out.println("onMessage");
		TextMessage mess = (TextMessage) message;
		try {
			System.out.println(mess.getText());
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
