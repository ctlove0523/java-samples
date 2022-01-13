package io.github.ctlove0523.javasamples.logcaptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogCaptorService {
    private static final Logger log = LoggerFactory.getLogger(LogCaptorService.class);

    public void commonLog() {
        log.debug("log captor debug log");
        log.info("log captor info log");
        log.warn("log captor warn log");
        log.error("log captor error log");
    }

    public void oddNumber(int number) {
        if (number % 2 == 1) {
            log.info("input is odd number");
        }

        log.info("input is even number");
    }

    public void logSwitch() {
        if (log.isDebugEnabled()) {
            log.debug("log enable debug");
        }

        log.info("log enable info");
    }
}
