package fr.eyeneed.web;

import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import fr.eyeneed.entities.Utilisateurs;

@Component
public class MailSender {
	@Autowired
	private JavaMailSender javaMailSender;  
	 @Autowired
	   private MessageSource messages;
	 @Autowired 
	 private TemplateEngine templateEngine;
	 public void newPasswordMail(final Utilisateurs user, final Locale locale) 
	            throws MessagingException {
		 	ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml"); 
	        // Preparele context
	        final Context ctx = new Context(locale);
	        ctx.setVariable("user", user);
	        //ctx.setVariable("subscriptionDate", new Date());
	        //ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));

	        // Prepare le Mime et helper
	        final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
	        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
	        message.setSubject("Réinitialisation de votre mot de passe Eyeneed");
	        message.setFrom("contact@eyeneed.fr");
	        message.setTo(user.getMail());

	        // template Thymeleaf
	        final String htmlContent = this.templateEngine.process("mailNewPassword", ctx);
	        message.setText(htmlContent, true /* isHtml */);

	        // envoie le email
	        this.javaMailSender.send(mimeMessage);

	    }
}
