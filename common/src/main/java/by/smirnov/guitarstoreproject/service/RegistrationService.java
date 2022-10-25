package by.smirnov.guitarstoreproject.service;

import by.smirnov.guitarstoreproject.model.User;
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

@RequiredArgsConstructor
@Service
public class RegistrationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;

    @Transactional
    public void register(User object){
        object.setPassword(passwordEncoder.encode(object.getPassword()));
        repository.save(object);
    }

    public void sendVerificationEmail(User user, String siteURL)
            throws MessagingException, UnsupportedEncodingException {
        String toAddress = user.getEmail();
        String fromAddress = "antonjurist@yandex.ru";
        String senderName = "Guitar Store";
        String subject = "Please verify your registration";
        String content = "Hello, [[name]],<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName());
        String verifyURL = siteURL + "/verify?code=" + user.getVerificationCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);

    }

    @Transactional
    public boolean verify(String verificationCode) {
        User user = repository.findByVerificationCode(verificationCode);

        if (user == null || user.getIsEnabled()) {
            return false;
        } else {
            user.setVerificationCode(null);
            user.setIsEnabled(true);
            repository.save(user);

            return true;
        }

    }
}
