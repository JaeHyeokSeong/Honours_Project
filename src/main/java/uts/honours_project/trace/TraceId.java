package uts.honours_project.trace;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TraceId {

    private final String id;
    private final int level;

    private TraceId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    public TraceId() {
        id = UUID.randomUUID().toString();
        level = 0;
    }

    public TraceId createNextTraceId() {
        return new TraceId(id, level + 1);
    }

    public TraceId createPreviousTraceId() {
        return new TraceId(id, level - 1);
    }
}
