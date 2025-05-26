package uts.honours_project.trace;

import lombok.Getter;

@Getter
public class TraceStatus {

    private final TraceId traceId;
    private final long startTime;
    private final String message;

    public TraceStatus(TraceId traceId, String message) {
        this.traceId = traceId;
        this.startTime = System.currentTimeMillis();
        this.message = message;
    }
}
