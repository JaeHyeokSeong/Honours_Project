package uts.honours_project.web.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class MemberAddErrorResult {

    private final List<String> messages;

    public MemberAddErrorResult(List<String> messages) {
        this.messages = messages;
    }
}
