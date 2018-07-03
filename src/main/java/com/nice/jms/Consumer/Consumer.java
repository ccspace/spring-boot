package com.nice.jms.Consumer;

import com.nice.jms.queue.Producer;

import javax.jms.*;


/**
 * @program: nice-springboot
 * @description: 消费者
 * @author: BaoFei
 * @create: 2018-07-03 09:48
 **/
public class Consumer {

    private static final String queueName = "queue-test";

    public void consumer(){
        Connection conn = Producer.getConnection();
        try {
            conn.start();
            Session session = conn.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(queueName);
            //创建消费者
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println("消费消息:"+textMessage);
                }
            });
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            /*if(conn != null){
                try {
                    conn.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.consumer();
    }

}
