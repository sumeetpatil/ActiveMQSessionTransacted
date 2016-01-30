package ActiveMQSample.ActiveMQConsumer;

import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.BrokerService;

public class ActiveMQBroker {
	private static final int MAXIMUM_REDELIVERIES = -1; 
	private static final int REDELIVERY_DELAY = 30000;
	private static final String Q_NAME = "TEST";
	private static final String ACTIVEMQ_CONNECTION = "tcp://localhost:61617";
	BrokerService broker;

	ActiveMQBroker() {
		try {
			if (broker == null) {
				BrokerService broker = new BrokerService();
				broker.addConnector(ACTIVEMQ_CONNECTION);
				broker.start();

				System.out.println("****************** BROKER STARTED*******************");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	public void createConsumers(){
		try {

			RedeliveryPolicy policy = new RedeliveryPolicy();
			policy.setMaximumRedeliveries(MAXIMUM_REDELIVERIES);
			policy.setRedeliveryDelay(REDELIVERY_DELAY);
			policy.setMaximumRedeliveryDelay(REDELIVERY_DELAY);
			policy.setInitialRedeliveryDelay(REDELIVERY_DELAY);

			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_CONNECTION);
			ActiveMQConnection connection = null;
			connection = (ActiveMQConnection)connectionFactory.createConnection();
			connection.setRedeliveryPolicy(policy);
			connection.start();

			Session session = connection.createSession(true, Session.SESSION_TRANSACTED);

			Destination destination = session.createQueue(Q_NAME);

			MessageConsumer consumer = session.createConsumer(destination);

			consumer.setMessageListener(new QListener(session));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
