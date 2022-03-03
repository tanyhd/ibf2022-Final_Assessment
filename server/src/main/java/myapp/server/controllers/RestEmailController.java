package myapp.server.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestEmailController {
    @RequestMapping(path = "/sendemail/{id}")
    public String sendEmail(@PathVariable String id) throws AddressException, MessagingException, IOException {
        sendmail(id);
        return "Email sent successfully";
    }

    private void sendmail(String id) throws AddressException, MessagingException, IOException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
           protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication("dtanyhmail@gmail.com", System.getenv("GOOGLE_APP_PASSWORD"));
           }
        });
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("\"The Food App\" <dtanyhmail@gmail.com>", false));
     
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("darren243@hotmail.com"));
        msg.setSubject("Test email from spring boot" + id);
        msg.setContent("Test contents", "text/html");
        msg.setSentDate(new Date());
     
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent("Test message body contents", "text/html");
     
        MimeMultipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        MimeBodyPart attachPart = new MimeBodyPart();
     
        attachPart.attachFile("./src/main/resources/static/images/test 1.jpg");
        multipart.addBodyPart(attachPart);
        msg.setContent(multipart);
        Transport.send(msg);   
     }
}
