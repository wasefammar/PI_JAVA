package services;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
    public static void sendEmail(String recipient, String subject, String body) {
        // Set properties for the SMTP server
        Properties properties = new Properties();
        System.out.println("AAAAAAAAAAA"+recipient);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Replace these with your Gmail credentials
        String adresse_mail = "Bensalahons428@gmail.com";
        String mot_passe = "scvt toqa edhb fntg";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(adresse_mail, mot_passe);
            }
        });

        try {
            // Create a MimeMessage object
            MimeMessage message = new MimeMessage(session);

            // Set content type to "text/html"
            message.setContent(body, "text/html; charset=utf-8");
            message.setFrom(new InternetAddress(adresse_mail));

            // Set recipient
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));

            // Set email subject
            message.setSubject(subject);

            // Send the email
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès à " + recipient);
        } catch (MessagingException e) {
            System.err.println("Erreur d'envoi de l'e-mail : " + e.getMessage());
        }
    }
}
