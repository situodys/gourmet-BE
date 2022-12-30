package kw.ic.backend.global.mail;

import java.net.URLDecoder;
import kw.ic.backend.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final MailUtil mailUtil;
    private final MemberRepository memberRepository;

    public String sendAuthCodeEmail(String userEmailAddress) throws Exception {
        String authCode = mailUtil.generateAuthCode();

        mailUtil.sendMail(userEmailAddress, authCode);

        log.info("{}: {}", userEmailAddress, authCode);

        return authCode;
    }

    public Boolean isAlreadySignupEmail(String emailAddress) {
        return memberRepository.existsByEmail(emailAddress);
    }
}
