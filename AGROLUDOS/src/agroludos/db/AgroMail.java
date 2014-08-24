package agroludos.db;

import java.util.Properties;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;

public class AgroMail
{
    private static final String CHOSEN_SERVER_ADDRESS = "smtp.gmail.com";
    private static final int CHOSEN_SERVER_PORT = 587;
    private static final String CHOSEN_USERNAME = "agroludos.ingegneria@gmail.com";
    private static final String CHOSEN_PASSWORD = "agroludosL&L";
    
    String from, to, subject, text;
    
    public AgroMail(String from, String to, String subject, String text)
    {
        this.from = CHOSEN_USERNAME;
        this.to = to;
        this.subject = subject + " (" + from + ")";
        this.text = text;
    }
    
    public void send() throws AddressException, MessagingException
    {
        Properties p = new Properties();
        p.put("mail.smtp.host", CHOSEN_SERVER_ADDRESS);
        p.put("mail.smpt.port", String.valueOf(CHOSEN_SERVER_PORT));
        p.put("mail.smtp.starttls.enable", "true");
        p.put("mail.smtp.auth", "true");
        p.put("mail.smtp.socketFactory.fallback", "false");
        
        Session mailSession = Session.getDefaultInstance(p);
        Message message = new MimeMessage(mailSession);
        
        InternetAddress fromAddress = new InternetAddress(from);
        InternetAddress toAddress = new InternetAddress(to);
        
        message.setFrom(fromAddress);
        message.setRecipient(RecipientType.TO, toAddress);
        message.setSubject(subject);
        message.setText(text);
        
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(CHOSEN_SERVER_ADDRESS, from, CHOSEN_PASSWORD);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}
