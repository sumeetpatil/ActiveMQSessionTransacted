package ActiveMQSample.ActiveMQConsumer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;

public class QListener implements MessageListener {
	Session session;

	public QListener(Session s) {
		session = s;
	}

	public void onMessage(Message message) {
		if(message.toString().contains("error_message")){
			try {
				session.rollback();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}else{
			try {
				session.commit();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}
}
