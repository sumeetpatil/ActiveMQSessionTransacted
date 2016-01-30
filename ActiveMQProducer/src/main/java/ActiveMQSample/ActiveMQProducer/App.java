package ActiveMQSample.ActiveMQProducer;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class App 
{
	//rollback - session rollback in consumer (Error Message)
	private static final String MESSAGE_TO_SEND = "error_message";
	//No rollback - session commit in consumer (Success Message)
//	private static final String MESSAGE_TO_SEND = "success message";
	private static final String Q_NAME = "TEST";
	private static final String ACTIVEMQ_CONNECTION = "tcp://localhost:61617";
	
    public static void main( String[] args )
    {
    	
    	try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_CONNECTION);

			Connection connection = connectionFactory.createConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination destination = session.createQueue(Q_NAME);

			MessageProducer producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

			String text = MESSAGE_TO_SEND;
			TextMessage message = session.createTextMessage(text);
			producer.send(message);

			session.close();
			connection.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
}
