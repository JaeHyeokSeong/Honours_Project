package uts.honours_project.aop;

import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class AServiceTest {

    @Autowired
    AService service;

    @Test
    void aopTest() {
        service.callWithTimeLog();
        service.callWithoutTimeLog();

        assertThat(AopUtils.isCglibProxy(service)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(service)).isFalse();
    }
}
