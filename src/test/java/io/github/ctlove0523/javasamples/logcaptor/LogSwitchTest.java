package io.github.ctlove0523.javasamples.logcaptor;

import static org.assertj.core.api.Assertions.assertThat;
import nl.altindag.log.LogCaptor;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LogSwitchTest {
    private static LogCaptor logCaptor;

    @BeforeClass
    public static void init() {
        logCaptor = LogCaptor.forClass(LogCaptorService.class);
    }

    @Before
    public void cleanLogs() {
        logCaptor.clearLogs();
    }

    @AfterClass
    public static void close() {
        logCaptor.close();
    }

    @Test
    public void testLogSwitch_should_notLogDebugMsg_when_logInfoOn() {
        LogCaptorService logCaptorService = new LogCaptorService();
        logCaptor.setLogLevelToInfo();

        logCaptorService.logSwitch();

        assertThat(logCaptor.getInfoLogs()).containsExactly("log enable info");
        assertThat(logCaptor.getLogs().size()).isEqualTo(1);
    }

    @Test
    public void testLogSwitch_should_logDebugMsg_when_logDebugOn() {
        LogCaptorService logCaptorService = new LogCaptorService();
        logCaptor.setLogLevelToDebug();

        logCaptorService.logSwitch();

        assertThat(logCaptor.getInfoLogs()).containsExactly("log enable info");
        assertThat(logCaptor.getDebugLogs()).containsExactly("log enable debug");
        assertThat(logCaptor.getLogs().size()).isEqualTo(2);
    }
}
