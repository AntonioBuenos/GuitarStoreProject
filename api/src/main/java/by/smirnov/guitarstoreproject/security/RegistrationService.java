package by.smirnov.guitarstoreproject.security;

import by.smirnov.guitarstoreproject.domain.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface RegistrationService {

    User register(User object);

    void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException;

    boolean verify(String verificationCode);
}
