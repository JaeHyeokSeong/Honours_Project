package uts.honours_project.trace;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogTrace {

    private final ThreadLocal<TraceId> traceIdHolder = new ThreadLocal<>();
    private static final String START_PREFIX = "-->";
    private static final String END_PREFIX = "<--";
    private static final String END_WITH_EX_PREFIX = "<X-";

    public TraceStatus begin(String message) {
        TraceStatus traceStatus = new TraceStatus(findTraceId(), message);
        log.info("[Id={}] {} {}", traceStatus.getTraceId().getId(),
                displayPrefix(START_PREFIX, traceStatus.getTraceId().getLevel()), message);
        return traceStatus;
    }

    public void end(TraceStatus traceStatus) {
        long totalTime = System.currentTimeMillis() - traceStatus.getStartTime();
        log.info("[Id={}] {} {} [time={}ms]", traceStatus.getTraceId().getId(),
                displayPrefix(END_PREFIX, traceStatus.getTraceId().getLevel()), traceStatus.getMessage(), totalTime);
        releaseTraceId();
    }

    public void endWithException(TraceStatus traceStatus, Exception e) {
        long totalTime = System.currentTimeMillis() - traceStatus.getStartTime();
        log.info("[Id={}] {} {} [time={}ms] [ex={}]", traceStatus.getTraceId().getId(),
                displayPrefix(END_WITH_EX_PREFIX, traceStatus.getTraceId().getLevel()), traceStatus.getMessage(), totalTime, e.toString());
        releaseTraceId();
    }

    private void releaseTraceId() {
        TraceId traceId = traceIdHolder.get();
        if (traceId == null) {
            throw new IllegalStateException("There is no traceId to end." +
                    " Please call begin(String) first, then call this method.");
        }

        if (traceId.getLevel() == 0) {
            traceIdHolder.remove();
        } else {
            traceIdHolder.set(traceId.createPreviousTraceId());
        }
    }

    private TraceId findTraceId() {
        TraceId findTraceId = traceIdHolder.get();
        //If traceId is null, it means that the current thread does not have a traceId.
        if (findTraceId == null) {
            TraceId traceId = new TraceId();
            traceIdHolder.set(traceId);
        } else {
            traceIdHolder.set(findTraceId.createNextTraceId());
        }

        return traceIdHolder.get();
    }

    // --> level 0
    //   |--> level 1
    //   |  |--> level 2
    //   |  |<-- level 2 (return)
    private String displayPrefix(String prefix, int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level + 1; i++) {
            if (i == level) {
                sb.append(prefix);
            } else {
                sb.append("  |");
            }
        }
        return sb.toString();
    }
}
