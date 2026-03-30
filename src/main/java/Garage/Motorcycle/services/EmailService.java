package Garage.Motorcycle.services;

import Garage.Motorcycle.mail.AbstractEmailContext;
import Garage.Motorcycle.mail.EmailInterface;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.SpringTemplateLoader;


import java.nio.charset.StandardCharsets;

@Service
public class EmailService implements EmailInterface {

    //getting email sender
    @Autowired
    private JavaMailSender javaMailSender;

    //getting Spring template engine
    @Autowired
    private SpringTemplateLoader springTemplateLoader;


    //overriding send Email function
    @Override
    public void sendEmail(AbstractEmailContext abstractEmailContext) throws MessagingException {
        //creating message
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
        //creating Context

    }
}
