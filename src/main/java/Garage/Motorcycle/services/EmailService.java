package Garage.Motorcycle.services;

import Garage.Motorcycle.mail.AbstractEmailContext;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService{

    //getting email sender
    @Autowired
    private JavaMailSender javaMailSender;

    //getting Spring template engine
    @Autowired
    private SpringTemplateEngine springTemplateEngine;


    //overriding send Email function

    public void sendEmail(AbstractEmailContext abstractEmailContext) throws MessagingException {
        //creating message
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_RELATED, StandardCharsets.UTF_8.name());
        //creating Context
        Context context=new Context();
        //setting variables
        context.setVariables(abstractEmailContext.getContext());
        //creating email content
        String emailContent=springTemplateEngine.process(abstractEmailContext.getTemplateLocation(), context);
        mimeMessageHelper.setTo(abstractEmailContext.getTo());
        mimeMessageHelper.setFrom(abstractEmailContext.getFrom());
        mimeMessageHelper.setSubject(abstractEmailContext.getSubject());
        mimeMessageHelper.setText(emailContent, true);


        javaMailSender.send(message);
    }
}
