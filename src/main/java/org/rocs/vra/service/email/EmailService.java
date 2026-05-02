package org.rocs.vra.service.email;

import jakarta.mail.*;

import java.util.Date;
import java.util.Properties;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import static org.rocs.vra.domain.email.constant.EmailConstant.*;

/**
 *  Service responsible for sending email notifications to users.
 */
@Service
public class EmailService {

    /**
     * Sends a newly generated password to a user's email address.
     * Creates a MIME message, connects to the Gmail SMTP server using the configured
     * credentials, and transmits the message to the recipient.
     */
    public void sendNewPasswordEmail(String firstName, String password, String email) throws MessagingException {
        Message message = createEmail(firstName, password, email);
        Transport smtpTransport = (Transport) getEmailSession().getTransport(SIMPLE_MAIL_TRANSFER_PROTOCOL);
        smtpTransport.connect(GMAIL_SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    /**
     * Constructs a MIME message containing the password notification.
     * The message includes a personalized greeting, the new password,
     * and a closing line from the support team.
     */
    private Message createEmail(String firstName, String password, String email) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(EMAIL_SUBJECT);
        message.setText("Hi " + firstName + ", \n \n Your new account password is:" + password + "\n \n The Support Team");
        message.setSentDate(new Date());
        message.saveChanges();
        return message;
    }

    /**
     * Creates and configures a JavaMail Session with Gmail SMTP properties.
     * Sets the SMTP host, authentication, port, and STARTTLS requirements
     * as defined in EmailConstant.
     */
    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, GMAIL_SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }

}
