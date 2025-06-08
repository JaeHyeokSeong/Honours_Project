package uts.honours_project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeAspect {

    @Pointcut("execution(* uts.honours_project.aop.AService.callWithTimeLog())")
    private void aServiceWithTime() {}

    @Around("aServiceWithTime()")
    public Object timeLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        log.info("startTime={}ms", startTime);

        //call target method
        Object result = proceedingJoinPoint.proceed();

        long endTime = System.currentTimeMillis();
        log.info("endTime={}ms", endTime);
        log.info("totalTime={}ms", endTime - startTime);
        return result;
    }
}
