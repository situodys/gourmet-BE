package kw.ic.backend.global.mail;

import java.util.UUID;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailUtil {

    @Value("${mail.admin.id}")
    private String adminId;

    private final JavaMailSender javaMailsender;

    public String generateAuthCode() {
        return UUID.randomUUID().toString().substring(0, 10);
    }

    @Async
    public void sendMail(String userEmailAddress, String authCode) {
        try {
            javaMailsender.send(createAuthCodeEmail(userEmailAddress, authCode));
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private MimeMessage createAuthCodeEmail(String userEmailAddress, String authCode) throws Exception {
        MimeMessage email = javaMailsender.createMimeMessage();
        fillEmail(userEmailAddress, email, authCode);

        return email;
    }

    private void fillEmail(String userEmailAddress, MimeMessage email, String authCode) throws Exception {
        email.addRecipients(RecipientType.TO, userEmailAddress);//보내는 대상
        email.setSubject("KWICWEB_13Team 이메일 인증");//제목

        StringBuilder msg = new StringBuilder();
        msg.append("<div style='margin:100px;'>");
        msg.append("<h1> 안녕하세요 KWICWEB_13Team 입니다. </h1>");
        msg.append("<br>");
        msg.append("<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>");
        msg.append("<br>");
        msg.append("<p>감사합니다!<p>");
        msg.append("<br>");
        msg.append("<div align='center' style='border:1px solid black; font-family:verdana';>");
        msg.append("<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>");
        msg.append("<div style='font-size:130%'>");
        msg.append("CODE : <strong>" + authCode + "</strong><div><br/> ");
        msg.append("</div>");
        email.setText(msg.toString(), "utf-8", "html");//내용
        email.setFrom(new InternetAddress(adminId, "KWICWEB_13Team"));//보내는 사람
    }


}
