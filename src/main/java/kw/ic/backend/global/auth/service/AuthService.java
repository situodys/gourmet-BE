package kw.ic.backend.global.auth.service;

import java.util.Optional;
import kw.ic.backend.domain.member.Member;
import kw.ic.backend.domain.member.Member.MemberBuilder;
import kw.ic.backend.domain.member.dto.SimpleMember;
import kw.ic.backend.domain.member.dto.embbed.Authority;
import kw.ic.backend.domain.member.repository.MemberRepository;
import kw.ic.backend.global.auth.dto.AuthRequest;
import kw.ic.backend.global.auth.dto.Tokens;
import kw.ic.backend.global.auth.util.JwtProvider;
import kw.ic.backend.global.exception.CustomException;
import kw.ic.backend.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public Long signup(AuthRequest request) {

        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .authority(Authority.ROLE_USER)
                .build();

        return memberRepository.save(member).getId();
    }

    public Tokens login(AuthRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    throw new CustomException(ErrorCode.INVALID_LOGIN_EMAIL);
                });

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_LOGIN_PASSWORD);
        }
        SimpleMember info = new SimpleMember(member.getId(), member.getEmail(), member.getAuthority());
        return new Tokens(jwtProvider.createAccessToken(info), jwtProvider.createRefreshToken(info));
    }
}
