
package DarkDev.services;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class EmailSender {
    public static void sendEmail(String recipient, String subject, String body, String attachmentPath) {
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

            // Create a multipart message for body and attachment
            MimeMultipart multipart = new MimeMultipart();

// Part 1: Text message body
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setContent(body, "text/plain");
            multipart.addBodyPart(messagePart);

// Part 2: Attachment
            messagePart = new MimeBodyPart();
            DataHandler handler = new DataHandler(new FileDataSource(attachmentPath));
            messagePart.setDataHandler(handler);
            messagePart.setHeader("Content-Disposition", "attachment; filename=" + new File(attachmentPath).getName());
            multipart.addBodyPart(messagePart);

// Set the message content as the multipart message
            message.setContent(multipart);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            // Send the email
            Transport.send(message);

            System.out.println("E-mail envoyé avec succès à " + recipient);
        } catch (MessagingException e) {
            System.err.println("Erreur d'envoi de l'e-mail : " + e.getMessage());
        }
    }

}
