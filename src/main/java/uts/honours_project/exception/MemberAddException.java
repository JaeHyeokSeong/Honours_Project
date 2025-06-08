package uts.honours_project.exception;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class MemberAddException extends RuntimeException {

    private final List<ObjectError> errors;

    public MemberAddException(List<ObjectError> errors) {
        this.errors = errors;
    }
}
