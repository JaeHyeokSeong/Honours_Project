package uts.honours_project.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import uts.honours_project.domain.entity.Member;
import uts.honours_project.domain.service.MemberService;
import uts.honours_project.web.controller.dto.MemberDto;
import uts.honours_project.web.controller.dto.MemberFormDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member")
    public String memberAddForm(@ModelAttribute("memberFormDto") MemberFormDto form) {
        return "member/memberAddForm";
    }

    @PostMapping("/member")
    public String memberAdd(@Valid @ModelAttribute MemberFormDto form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "member/memberAddForm";
        }

        Member member = new Member(form.getName(), form.getAge());
        memberService.joinMember(member);

        return "redirect:/members";
    }

    @GetMapping("/members")
    public String members(Model model) {
        List<MemberDto> findMembers = memberService.findAllMembers().stream()
                .map(MemberDto::new)
                .toList();

        model.addAttribute("members", findMembers);
        return "member/members";
    }
}
