package uts.honours_project.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uts.honours_project.domain.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
