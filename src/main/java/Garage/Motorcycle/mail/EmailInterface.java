package Garage.Motorcycle.mail;

import jakarta.mail.MessagingException;

public interface EmailInterface {
    void sendEmail(AbstractEmailContext abstractEmailContext) throws MessagingException;
}
