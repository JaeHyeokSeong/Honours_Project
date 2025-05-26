package uts.honours_project.web.controller.dto;

import lombok.Data;
import uts.honours_project.domain.entity.Member;

@Data
public class MemberDto {

    private Long id;
    private String name;
    private Integer age;

    public MemberDto(Member member) {
        id = member.getId();
        name = member.getName();
        age = member.getAge();
    }
}
