package agroludos.db;

import java.util.Properties;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;

public class AgroMail
{
    private static final String CHOSEN_SERVER_ADDRESS = "smtp.gmail.com";
    private static final int CHOSEN_SERVER_PORT = 465;
    
    String from, to, subject, text;
    
    public AgroMail(String from, String to, String subject, String text)
    {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }
    
    public void send() throws AddressException, MessagingException
    {
        Properties p = new Properties();
        p.put("mail.smtp.host", CHOSEN_SERVER_ADDRESS);
        p.put("mail.smpt.port", String.valueOf(CHOSEN_SERVER_PORT));
        
        Session mailSession = Session.getDefaultInstance(p);
        Message message = new MimeMessage(mailSession);
        
        InternetAddress fromAddress = new InternetAddress(from);
        InternetAddress toAddress = new InternetAddress(to);
        
        message.setFrom(fromAddress);
        message.setRecipient(RecipientType.TO, toAddress);
        message.setSubject(subject);
        message.setText(text);
    }
}
