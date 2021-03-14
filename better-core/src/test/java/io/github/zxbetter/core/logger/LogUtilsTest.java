package io.github.zxbetter.core.logger;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

import static org.mockito.Mockito.*;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class LogUtilsTest {

    /**
     * 日志常量
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtilsTest.class);

    @Mock
    private Consumer<Logger> print;

    @Test
    public void log() {

        doNothing().when(this.print).accept(any(Logger.class));

        LogUtils.log(log, LOGGER, this.print);
        LogUtils.log(null, LOGGER, this.print);

        verify(this.print, times(2)).accept(any(Logger.class));
    }
}
