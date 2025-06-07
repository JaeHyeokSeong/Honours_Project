package uts.honours_project.dynamic_proxy.jdk;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void proxyAImpl() {
        //given
        AImpl target = new AImpl();

        //when
        AInterface proxy = (AInterface) Proxy.newProxyInstance(
                AInterface.class.getClassLoader(),
                new Class[]{AInterface.class},
                new TimeInvocationHandler(target)
        );
        proxy.call();

        //then
        assertThat(Proxy.isProxyClass(proxy.getClass())).isTrue();
        assertThat(Proxy.isProxyClass(target.getClass())).isFalse();
        log.info("proxy={}", proxy.getClass());
    }
}
