package mordaka.arkadiusz.application.service;

public interface MailSenderService {

    void sendEmail(String to, String subject, String content);
}
