# ActiveMQSessionTransacted
ActiveMQ with re-delivery of messages if session is rolled back

- Run ActiveMQConsumer to start the broker and start listening to the TEST Queue
- Change the code to send error message or success message in ActiveMQProducer and run to test session rollback and commit
