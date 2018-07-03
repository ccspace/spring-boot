package com.nice.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @program: nice-springboot
 * @description: activeMQ
 * @author: BaoFei
 * @create: 2018-07-03 08:53
 **/
public class Producer {

    private static final String url = "tcp://192.168.44.1:61616";
    private static final String queueName = "queue-test";

    public static Connection getConnection(){
        //1、创建链接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2、创建链接
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void sendMsg(){
        Connection conn = getConnection();
        try {
            //3、启动链接
            conn.start();
            //4、创建会话
            Session session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
            //5、创建目标
            Destination destination = session.createQueue(queueName);
            //6、创建一个生产者
            MessageProducer producer = session.createProducer(destination);
            for (int i = 0; i < 10; i++) {
                TextMessage textMessage = session.createTextMessage("hello nice!" + i);
                producer.send(textMessage);
                System.out.println("发送消息" + textMessage.getText());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if(conn != null){
                try {
                    conn.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Producer producer = new Producer();
        producer.sendMsg();
    }
}
