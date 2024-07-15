package com.easy_way_bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class MailConfig {

    @Bean
    public static JavaMailSenderImpl getMailConfig() {
        JavaMailSenderImpl emailConfig = new JavaMailSenderImpl();
        Properties props = emailConfig.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        emailConfig.setHost("sandbox.smtp.mailtrap.io");
        emailConfig.setPort(2525);
        emailConfig.setUsername("c2b0c90b681e90");
        emailConfig.setPassword("47b03ddc685932");
        return emailConfig;
    }
}
