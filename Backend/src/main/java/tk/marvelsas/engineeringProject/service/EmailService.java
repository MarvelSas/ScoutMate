package tk.marvelsas.engineeringProject.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.Email;

@Service
@AllArgsConstructor
public class EmailService implements EmailSander   {

    private final static Logger LOGGER= LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender javaMailSender;

    @Override
    @Async
    public void send(String to, String email, String type) {
        try {
            MimeMessage message=javaMailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,"utf-8");
            helper.setText(email,true);
            helper.setTo(to);
            if(type.equals("resetPassword")){
                helper.setSubject("Zresetuj swoje hasło");
            }else {
                helper.setSubject("Potwierdź swój adres email");
            }
            helper.setFrom("engineeringprojectwebapp@gmail.com");
            javaMailSender.send(message);
        }catch (MessagingException e){
            LOGGER.error("Fail Sand Email",e);
            throw new IllegalStateException("Fail Sand Email");
        }

    }
}
