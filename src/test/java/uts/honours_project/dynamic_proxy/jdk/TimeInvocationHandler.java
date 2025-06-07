package uts.honours_project.dynamic_proxy.jdk;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
@RequiredArgsConstructor
public class TimeInvocationHandler implements InvocationHandler {

    private final Object target;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info("startTime={}ms", startTime);

        //call target method
        Object result = method.invoke(target, args);

        long endTime = System.currentTimeMillis();
        log.info("endTime={}ms", endTime);
        log.info("totalTime={}ms", endTime - startTime);
        return result;
    }
}
