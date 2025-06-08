package uts.honours_project.aop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AService {

    public void callWithTimeLog() {
        log.info("AService.callWithTimeLog");
    }

    public void callWithoutTimeLog() {
        log.info("AService.callWithoutTimeLog");
    }
}
