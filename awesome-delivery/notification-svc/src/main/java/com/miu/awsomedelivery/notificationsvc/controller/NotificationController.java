package com.miu.awsomedelivery.notificationsvc.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.miu.awsomedelivery.notificationsvc.Enum.OrderStatus;
import com.miu.awsomedelivery.notificationsvc.model.Order;
import com.miu.awsomedelivery.notificationsvc.services.EmailService;
import com.wildbit.java.postmark.Postmark;
import com.wildbit.java.postmark.client.ApiClient;
import com.wildbit.java.postmark.client.data.model.message.Message;
import com.wildbit.java.postmark.client.data.model.message.MessageResponse;
import com.wildbit.java.postmark.client.exception.PostmarkException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);
    @Autowired
    private EmailService emailService;
    private static final String subject = "Order Delivered!";
    @Autowired
    private ObjectMapper objectMapper;


    // Delivered status
//    @KafkaListener(topics = "#{'${app.topic-name}'}", groupId = "#{'${spring.kafka.consumer.group-id}'}")
//    @KafkaListener(topics = "topic-orderdelivered", groupId="consumer-driver")
    @KafkaListener(topics = "topic-orderdelivered")
    public void listenOrder(ConsumerRecord<String, String> cr, @Payload String orderMessage) {
        logger.info("Received Order [key={} Payload={}]", cr.key(), orderMessage);
        try {
            Order order= objectMapper.readValue(orderMessage,Order.class);
            if(!order.getStatus().equals(OrderStatus.DELIVERED)){
                return;
            }
            //send email to customer

            String sendtoEmail = order.getCustomer().getAddress().getEmail();
            //this.emailService.sendTextEmail(sendtoEmail,subject,"Order delivered!");

            try
            {
//                logger.info("Sending email!");
//                SimpleMailMessage msg = new SimpleMailMessage();
//                msg.setTo(sendtoEmail, "merajkhan.cse@gmail.com");
//                msg.setSubject(subject);
//                msg.setText("Order delivered!");
//
//                JavaMailSender javaMailSender = new JavaMailSenderImpl();
//                javaMailSender.send(msg);
//                logger.info("Email sent");
                String Id;
                Message message = new Message("sabbir@nsales.dk", sendtoEmail, subject, "Order delivered!");
                ApiClient client = Postmark.getApiClient("901ceb79-cac3-467b-96cb-b81c83e56352");
                try {
                    MessageResponse response = client.deliverMessage(message);
                    Id = response.getMessageId();
                }
                catch (PostmarkException e)
                {

                }
                catch (IOException e)
                {

                }

            }
            catch (Exception ex)
            {
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
