package com.miu.awsomedelivery.notificationsvc.services;


public interface EmailService {

    void sendTextEmail(String to,String subject,String text);

}
