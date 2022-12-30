package kw.ic.backend.domain.member.repository;

import java.util.Optional;
import kw.ic.backend.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member,Long> {

    public Optional<Member> findByEmail(String email);

    public boolean existsByEmail(String email);
}
