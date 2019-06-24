package zx.common.core.time;

import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static org.junit.Assert.*;

public class DateTimeUtilTest {

    private Date date;
    private LocalDateTime localDateTime;

    @Before
    public void before() {
        Instant instant = Instant.now();
        date = Date.from(instant);
        localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    @Test
    public void getDate() {
        assertEquals(date, DateTimeUtil.getDate(localDateTime));
    }

    @Test
    public void getLocalDateTime() {
        assertEquals(localDateTime, DateTimeUtil.getLocalDateTime(date));
    }

    @Test
    public void testOther() {
        System.out.println(DateTimeUtil.format(DateTimeUtil.getStartOfDay(localDateTime)));
        System.out.println(DateTimeUtil.format(DateTimeUtil.getEndOfDay(localDateTime)));
        System.out.println(DateTimeUtil.format(DateTimeUtil.getFirstDayOfMonth(localDateTime)));
        System.out.println(DateTimeUtil.format(DateTimeUtil.getLastDayOfMonth(localDateTime)));
        System.out.println(DateTimeUtil.format(DateTimeUtil.getLastDateOfMonth(localDateTime)));
    }
}
