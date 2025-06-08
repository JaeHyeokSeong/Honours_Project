package uts.honours_project.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uts.honours_project.domain.entity.Member;
import uts.honours_project.domain.repository.MemberRepository;
import uts.honours_project.domain.service.dto.MemberDto;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void joinMember(Member member) {
        memberRepository.save(member);
    }

    public List<MemberDto> findAllMembers() {
        return memberRepository.findAll().stream()
                .map(MemberDto::new)
                .toList();
    }
}
