package fiveguys.Tom.Cafeteria.Server.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class EmailLoginServiceImpl implements EmailLoginService {
    private final JavaMailSender emailSender;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static String generateAuthCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendAuthCode(String email) {
        SimpleMailMessage simpleMailMessage = createEmailForm(email);
        emailSender.send(simpleMailMessage);
    }


    private SimpleMailMessage createEmailForm(String email){
        String authCode = generateAuthCode(8);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("탐식당 회원가입 인증 번호");
        mailMessage.setFrom(sender);
        mailMessage.setText(authCode);
        return mailMessage;
    }
}
