package uts.honours_project.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import uts.honours_project.trace.LogTrace;
import uts.honours_project.trace.TraceStatus;

@Aspect
@RequiredArgsConstructor
public class TraceAspect {

    private final LogTrace logTrace;

    @Pointcut("execution(* uts.honours_project.web.controller..*.*(..))" +
            " && !execution(* uts.honours_project.web.controller..*.*ExHandle(..))")
    private void allController() {}

    @Pointcut("execution(* uts.honours_project.domain.service..*.*(..))")
    private void allService() {}

    @Pointcut("execution(* uts.honours_project.domain.repository..*.*(..))")
    private void allRepository() {}

    @Around("allController() || allService() || allRepository()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodSignature = joinPoint.getSignature().toString();
        TraceStatus traceStatus = logTrace.begin(methodSignature);

        try {
            Object result = joinPoint.proceed();
            logTrace.end(traceStatus);
            return result;
        } catch (Exception e) {
            logTrace.endWithException(traceStatus, e);
            throw e;
        }
    }
}
