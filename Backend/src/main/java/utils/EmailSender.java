package utils;

import exceptions.EmailSenderException;
import models.Flower;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private static final String senderEmail = "wtehnologii@gmail.com";

    public static void sendEmail(String recipientEmail, String flowerName) throws EmailSenderException {


        String smtpHost = "smtp.gmail.com";
        int smtpPort = 587;

        String username = "wtehnologii@gmail.com";
        String password = "sravkgspqorcxbuj";

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Create a session with authentication
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create a new email message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Flower Tool Notify");
            message.setText("You are receiving this email since the flower " + flowerName + " is now available!");

            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            throw new EmailSenderException("Some problems encountered when sending the email: " + e.getCause());
        }
    }

}
