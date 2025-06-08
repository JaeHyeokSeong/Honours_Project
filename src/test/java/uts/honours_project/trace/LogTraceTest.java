package uts.honours_project.trace;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(OutputCaptureExtension.class)
class LogTraceTest {

    @Test
    @DisplayName("Verify that each method's total execution time from invocation to response is logged in milliseconds")
    void begin_end_time(CapturedOutput capturedOutput) {
        //given
        LogTrace logTrace = new LogTrace();

        //when
        TraceStatus status = logTrace.begin("method1");
        logTrace.end(status);

        //then
        assertThat(capturedOutput.toString()).contains("[time=", "ms]");
    }

    @Test
    @DisplayName("In the case of normal flow")
    void begin_end_level3(CapturedOutput capturedOutput) {
        //given
        LogTrace logTrace = new LogTrace();

        //when
        TraceStatus status1 = logTrace.begin("method1");
        TraceStatus status2 = logTrace.begin("method2");
        TraceStatus status3 = logTrace.begin("method3");

        logTrace.end(status3);
        logTrace.end(status2);
        logTrace.end(status1);

        //then
        assertThat(capturedOutput.toString()).contains("--> method1");
        assertThat(capturedOutput.toString()).contains("  |--> method2");
        assertThat(capturedOutput.toString()).contains("  |  |--> method3");
        assertThat(capturedOutput.toString()).contains("  |  |<-- method3");
        assertThat(capturedOutput.toString()).contains("  |<-- method3");
        assertThat(capturedOutput.toString()).contains("<-- method3");
    }

    @Test
    @DisplayName("In the case of an exception flow")
    void begin_endWithException_level3(CapturedOutput capturedOutput) {
        //given
        LogTrace logTrace = new LogTrace();
        IllegalStateException ex = new IllegalStateException();

        //when
        TraceStatus status1 = logTrace.begin("method1");
        TraceStatus status2 = logTrace.begin("method2");
        TraceStatus status3 = logTrace.begin("method3");

        logTrace.endWithException(status3, ex);
        logTrace.endWithException(status2, ex);
        logTrace.endWithException(status1, ex);

        //then
        assertThat(capturedOutput.toString()).contains("--> method1");
        assertThat(capturedOutput.toString()).contains("  |--> method2");
        assertThat(capturedOutput.toString()).contains("  |  |--> method3");
        assertThat(capturedOutput.toString()).contains("  |  |<X- method3", "[ex=" + ex + "]");
        assertThat(capturedOutput.toString()).contains("  |<X- method3", "[ex=" + ex + "]");
        assertThat(capturedOutput.toString()).contains("<X- method3", "[ex=" + ex + "]");
    }

    @Test
    void multi_threads() throws InterruptedException {
        //given
        LogTrace logTrace = new LogTrace();
        AtomicReference<TraceStatus> reference1 = new AtomicReference<>();
        AtomicReference<TraceStatus> reference2 = new AtomicReference<>();

        //when
        Thread thread1 = new Thread(() -> {
            TraceStatus status = logTrace.begin("thread1 call method");
            logTrace.end(status);
            reference1.set(status);
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            TraceStatus status = logTrace.begin("thread2 call method");
            logTrace.end(status);
            reference2.set(status);
        });
        thread2.start();

        //then
        Thread.sleep(100);
        assertThat(reference1.get().getTraceId().getId())
                .isNotEqualTo(reference2.get().getTraceId().getId());
    }
}