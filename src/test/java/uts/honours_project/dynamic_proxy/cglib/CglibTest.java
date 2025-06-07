package uts.honours_project.dynamic_proxy.cglib;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class CglibTest {

    @Test
    void cglibTest() {
        //given
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(BConcrete.class);
        enhancer.setCallback(new TimeMethodInterceptor(new BConcrete()));

        //when
        BConcrete proxy = (BConcrete) enhancer.create();
        proxy.call();

        //then
        assertThat(proxy.getClass().getName()).contains("EnhancerByCGLIB");
        log.info("proxy={}", proxy.getClass());
    }
}
