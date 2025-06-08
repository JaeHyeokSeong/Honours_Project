package uts.honours_project.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uts.honours_project.domain.entity.Member;
import uts.honours_project.domain.service.MemberService;
import uts.honours_project.domain.service.dto.MemberDto;
import uts.honours_project.exception.MemberAddException;
import uts.honours_project.web.controller.dto.MemberAddErrorResult;
import uts.honours_project.web.controller.dto.MemberAddDto;

import java.util.List;
import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MessageSource messageSource;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public MemberAddErrorResult memberAddExHandle(MemberAddException e) {
        List<String> messages = e.getErrors().stream()
                .map(error -> messageSource.getMessage(error, Locale.ENGLISH))
                .toList();

        return new MemberAddErrorResult(messages);
    }

    @PostMapping("/member")
    public ResponseEntity<MemberAddDto> member(@Valid @RequestBody MemberAddDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new MemberAddException(bindingResult.getAllErrors());
        }

        Member member = new Member(dto.getName(), dto.getAge());
        memberService.joinMember(member);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/members")
    public List<MemberDto> members() {
        return memberService.findAllMembers();
    }
}
