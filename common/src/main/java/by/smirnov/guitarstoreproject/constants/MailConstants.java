package by.smirnov.guitarstoreproject.constants;

import static by.smirnov.guitarstoreproject.constants.CommonConstants.MAPPING_AUTH;

public interface MailConstants {

    String STORE_MAILING_ADDRESS = "antonjurist@yandex.ru";
    String SENDER_NAME = "Guitar Store";
    String MAIL_SUBJECT = "Please verify your registration";
    String MAIL_CONTENT = "Hello, [[name]],<br>"
            + "Please click the link below to verify your registration:<br>"
            + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
            + "Thank you,<br>"
            + "Your company name.";
    String MESSAGE_NAME = "[[name]]";
    String MESSAGE_URL = "[[URL]]";
    String VERIFICATION_URN = MAPPING_AUTH + "/verify?code=";
}
