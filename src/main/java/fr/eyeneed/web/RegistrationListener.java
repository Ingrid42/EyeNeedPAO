package fr.eyeneed.web;

import java.util.UUID;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import fr.eyeneed.dao.IUserService;
import fr.eyeneed.entities.OnRegistrationCompleteEvent;
import fr.eyeneed.entities.Utilisateurs;

@Component
@Service("mailService")
public class RegistrationListener implements
  ApplicationListener<OnRegistrationCompleteEvent> {
  
    @Autowired
    private IUserService service;
  
    @Autowired
   private MessageSource messages;
  
    @Autowired
    private JavaMailSender mailSender;
 
    @Override
  
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }
 
    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Utilisateurs user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml"); 
        String recipientAddress = user.getMail();
        String subject = "Registration Confirmation";
        String confirmationUrl 
          = event.getAppUrl() + "/verifierToken?token=" + token;
        //String message = messages.getMessage("message.regSucc", null, event.getLocale());
         
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setFrom("contact@eyeneed.fr");
        email.setReplyTo("contact@eyeneed.fr");
        
        //email.setText(message + " rn" + "http://localhost:8080" + confirmationUrl);
        email.setText(
        		"<html><body>"
        		+"<a href='http://localhost:8088" + confirmationUrl +"'>Activation de votre compte</a>"
        		+ "</body>"
        		+ "</html>");
        //System.out.println(email);
        mailSender.send(email);
    }
}