package uts.honours_project.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import uts.honours_project.domain.entity.Member;
import uts.honours_project.domain.repository.MemberRepository;
import uts.honours_project.web.controller.dto.MemberAddDto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("POST request test for /member success case")
    void member() throws Exception {
        //given
        MemberAddDto dto = new MemberAddDto();
        dto.setName("memberA");
        dto.setAge(10);
        String contentValue = objectMapper.writeValueAsString(dto);

        //when
        ResultActions result = mockMvc.perform(post("/member")
                .content(contentValue)
                .contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.name").value(dto.getName()));
        result.andExpect(jsonPath("$.age").value(dto.getAge()));
        assertThat(memberRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("POST request test for /member fail validation case")
    void memberFailValidation() throws Exception {
        //given
        MemberAddDto dto = new MemberAddDto();
        dto.setName("memberA");
        dto.setAge(200);
        String contentValue = objectMapper.writeValueAsString(dto);

        //when
        ResultActions result = mockMvc.perform(post("/member")
                .content(contentValue)
                .contentType(APPLICATION_JSON));

        //then
        result.andExpect(status().isBadRequest());
        result.andExpect(
                jsonPath("$.messages.[0]")
                        .value("must be less than or equal to 150")
        );
        assertThat(memberRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("GET request test for /members")
    void members() throws Exception {
        //given
        Member memberA = new Member("memberA", 10);
        Member memberB = new Member("memberB", 20);
        memberRepository.save(memberA);
        memberRepository.save(memberB);

        //when
        ResultActions result = mockMvc.perform(get("/members"));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(
                        jsonPath("$.[*].name", Matchers.contains(memberA.getName(), memberB.getName()))
                )
                .andExpect(
                        jsonPath("$.[*].age", Matchers.contains(memberA.getAge(), memberB.getAge()))
                );
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }
}