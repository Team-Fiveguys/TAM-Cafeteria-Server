package fiveguys.Tom.Cafeteria.Server.auth.service;

import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.auth.dto.LoginRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.common.RedisService;
import fiveguys.Tom.Cafeteria.Server.domain.user.service.UserQueryService;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class EmailLoginServiceImpl implements EmailLoginService {
    private final UserQueryService userQueryService;
    @Value("${spring.mail.username}")
    private String sender;
    private final JavaMailSender emailSender;
    private final RedisService redisService;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final Duration duration = Duration.ofMinutes(3);
    private BCryptPasswordEncoder passwordEncoder;


    @Override
    public void sendAuthCode(LoginRequestDTO.SendAuthCodeDTO loginFormDTO) {
        if(userQueryService.isExistByEmail(loginFormDTO.getEmail())){
            throw new GeneralException(ErrorStatus.EMAIL_DUPLICATED_ERROR);
        }
        String authCode = generateAuthCode(8);
        this.passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(loginFormDTO.getPassword());
//        redisService.setValue("USER:" + loginFormDTO.getEmail() + "name:", loginFormDTO.getName(), duration );
//        redisService.setValue("USER:" + loginFormDTO.getEmail() + "sex:", loginFormDTO.getSex().toString(), duration );
//        redisService.setValue("USER:" + loginFormDTO.getEmail() + "password:", encodedPassword, duration );
        redisService.setValue("USER:" + loginFormDTO.getEmail() + "authCode:", authCode, duration );
        SimpleMailMessage simpleMailMessage = createEmailForm(loginFormDTO.getEmail(), authCode);
        emailSender.send(simpleMailMessage);
    }

    private SimpleMailMessage createEmailForm(String email, String authCode){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("탐식당 회원가입 인증 번호");
        mailMessage.setFrom(sender);
        mailMessage.setText(authCode);
        return mailMessage;
    }
    private static String generateAuthCode(int length) {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }


    @Override
    public void verifyAuthCode(LoginRequestDTO.VerifyAuthCodeDTO verifyAuthCodeDTO) {
        String value = redisService.getValue("USER:" + verifyAuthCodeDTO.getEmail() + "authCode:");
        if(! (value.equals(verifyAuthCodeDTO.getAuthCode()))){
            throw new GeneralException(ErrorStatus.AUTHCODE_NOT_MATCH);
        }
    }

}
