package by.smirnov.guitarstoreproject.security;

import by.smirnov.guitarstoreproject.domain.User;
import by.smirnov.guitarstoreproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import static by.smirnov.guitarstoreproject.constants.MailConstants.MAIL_CONTENT;
import static by.smirnov.guitarstoreproject.constants.MailConstants.MAIL_SUBJECT;
import static by.smirnov.guitarstoreproject.constants.MailConstants.MESSAGE_NAME;
import static by.smirnov.guitarstoreproject.constants.MailConstants.MESSAGE_URL;
import static by.smirnov.guitarstoreproject.constants.MailConstants.SENDER_NAME;
import static by.smirnov.guitarstoreproject.constants.MailConstants.STORE_MAILING_ADDRESS;
import static by.smirnov.guitarstoreproject.constants.MailConstants.VERIFICATION_URN;

@RequiredArgsConstructor
@Service
public class RegistrationServiceImpl implements RegistrationService{

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Transactional
    @Override
    public User register(User object) {
        object.setPassword(passwordEncoder.encode(object.getPassword()));
        return repository.save(object);
    }

    @Override
    public void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(STORE_MAILING_ADDRESS, SENDER_NAME);
        helper.setTo(user.getEmail());
        helper.setSubject(MAIL_SUBJECT);
        helper.setText(getMailText(user, siteURL), true);

        mailSender.send(message);
    }

    private String getMailText(User user, String siteURL){
        String text = MAIL_CONTENT;
        text = text.replace(MESSAGE_NAME, user.getFirstName());
        String verifyURL = siteURL + VERIFICATION_URN + user.getVerificationCode();
        text = text.replace(MESSAGE_URL, verifyURL);
        return text;
    }

    @Transactional
    @Override
    public boolean verify(String verificationCode) {
        User user = repository.findByVerificationCode(verificationCode);

        if (Objects.isNull(user) || Boolean.TRUE.equals(user.getIsEnabled())) return false;

        user.setVerificationCode(null);
        user.setIsEnabled(true);
        repository.save(user);

        return true;
    }
}
