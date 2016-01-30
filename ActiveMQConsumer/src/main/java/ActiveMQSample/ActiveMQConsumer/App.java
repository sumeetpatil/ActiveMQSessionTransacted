package ActiveMQSample.ActiveMQConsumer;


public class App 
{
	public static void main( String[] args )
	{
		ActiveMQBroker amqBroker = new ActiveMQBroker();
		amqBroker.createConsumers();
	}
}
