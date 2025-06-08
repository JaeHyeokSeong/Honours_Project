package uts.honours_project.domain.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import uts.honours_project.domain.entity.Member;
import uts.honours_project.domain.service.dto.MemberDto;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    void joinMember() {
        //given
        Member memberA = new Member("memberA", 10);
        Member memberB = new Member("memberB", 20);

        //when
        memberService.joinMember(memberA);
        memberService.joinMember(memberB);

        //then
        List<MemberDto> findMembers = memberService.findAllMembers();
        assertThat(findMembers).extracting("name")
                .containsExactly(memberA.getName(), memberB.getName());
    }
}