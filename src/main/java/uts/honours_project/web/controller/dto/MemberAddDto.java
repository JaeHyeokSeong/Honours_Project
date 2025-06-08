package uts.honours_project.web.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class MemberAddDto {

    @NotBlank
    @Length(max = 50)
    private String name;

    @NotNull
    @Max(value = 150)
    private Integer age;
}
