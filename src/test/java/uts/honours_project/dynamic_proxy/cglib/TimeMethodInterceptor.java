package uts.honours_project.dynamic_proxy.cglib;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
public class TimeMethodInterceptor implements MethodInterceptor {

    private final Object target;

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info("startTime={}ms", startTime);

        //call target method
        Object result = proxy.invoke(target, args);

        long endTime = System.currentTimeMillis();
        log.info("endTime={}ms", endTime);
        log.info("totalTime={}ms", endTime - startTime);
        return result;
    }
}
