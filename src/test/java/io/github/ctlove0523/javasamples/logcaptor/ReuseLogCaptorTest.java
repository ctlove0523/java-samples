package io.github.ctlove0523.javasamples.logcaptor;

import static org.assertj.core.api.Assertions.assertThat;
import nl.altindag.log.LogCaptor;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReuseLogCaptorTest {
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
    public void testOddNumber_should_logOddMsg_when_inputIsOddNumber() {
        LogCaptor logCaptor = LogCaptor.forClass(LogCaptorService.class);
        LogCaptorService logCaptorService = new LogCaptorService();

        logCaptorService.oddNumber(1);

        assertThat(logCaptor.getInfoLogs()).containsExactly("input is odd number");
        assertThat(logCaptor.getLogs().size()).isEqualTo(1);
    }

    @Test
    public void testOddNumber_should_logEvenMsg_when_inputIsEvenNumber() {
        LogCaptor logCaptor = LogCaptor.forClass(LogCaptorService.class);
        LogCaptorService logCaptorService = new LogCaptorService();

        logCaptorService.oddNumber(2);

        assertThat(logCaptor.getInfoLogs()).containsExactly("input is even number");
        assertThat(logCaptor.getLogs().size()).isEqualTo(1);
    }
}
