package io.github.ctlove0523.javasamples.logcaptor;

import static org.assertj.core.api.Assertions.assertThat;
import nl.altindag.log.LogCaptor;
import org.junit.Test;

public class CommonLogTest {

    @Test
    public void testCommonLog_should_logMessage() {
        LogCaptorService logCaptorService = new LogCaptorService();
        LogCaptor logCaptor = LogCaptor.forClass(LogCaptorService.class);

        logCaptorService.commonLog();

        assertThat(logCaptor.getDebugLogs()).containsExactly("log captor debug log");
        assertThat(logCaptor.getInfoLogs()).containsExactly("log captor info log");
        assertThat(logCaptor.getWarnLogs()).containsExactly("log captor warn log");
        assertThat(logCaptor.getErrorLogs()).containsExactly("log captor error log");
        assertThat(logCaptor.getLogs().size()).isEqualTo(4);
    }
}
