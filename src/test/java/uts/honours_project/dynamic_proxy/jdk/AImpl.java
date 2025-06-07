package uts.honours_project.dynamic_proxy.jdk;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AImpl implements AInterface {
    @Override
    public void call() {
        log.info("AImpl.call");
    }
}
