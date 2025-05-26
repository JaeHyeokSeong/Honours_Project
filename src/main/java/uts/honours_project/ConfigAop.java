package uts.honours_project;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uts.honours_project.aop.TraceAspect;
import uts.honours_project.trace.LogTrace;

@Configuration
public class ConfigAop {

    @Bean
    public LogTrace logTrace() {
        return new LogTrace();
    }

    @Bean
    public TraceAspect traceAspect(LogTrace logTrace) {
        return new TraceAspect(logTrace);
    }
}
