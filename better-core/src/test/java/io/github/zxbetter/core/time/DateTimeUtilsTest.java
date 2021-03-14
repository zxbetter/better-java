package io.github.zxbetter.core.time;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class DateTimeUtilsTest {

    private static final int YEAR = 2021;

    private static final int MONTH = 3;

    private static final int CALENDAR_MONTH = Calendar.MARCH;

    private static final int DAY = 13;

    private static final int HOUR = 23;

    private static final int MINUTE = 21;

    private static final int SECOND = 40;

    private static final String FORMAT_DATE = YEAR + "-0" + MONTH + "-" + DAY;

    private static final String FORMAT_TIME = HOUR + ":" + MINUTE + ":" + SECOND;

    private Date globalDate;

    private LocalDateTime globalLocalDateTime;

    private DateTimeFormatter formatter;

    @Before
    public void setUp() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(YEAR, CALENDAR_MONTH, DAY, HOUR, MINUTE, SECOND);
        calendar.set(Calendar.MILLISECOND, 0);
        globalDate = calendar.getTime();

        globalLocalDateTime = LocalDateTime.of(YEAR, MONTH, DAY, HOUR, MINUTE, SECOND);

        formatter = DateTimeFormatter.ofPattern(DateTimeUtils.DEFAULT_DATE_TIME_FORMAT);
    }

    @Test
    public void convertToDate() {
        // 1. 参数为空时，返回当前时间
        assertNotNull(DateTimeUtils.convertToDate(null));

        // 2. 参数不为空时
        Date date = DateTimeUtils.convertToDate(this.globalLocalDateTime);
        assertNotNull(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        assertEquals(calendar.get(Calendar.YEAR), YEAR);
        assertEquals(calendar.get(Calendar.MONTH), CALENDAR_MONTH);
        assertEquals(calendar.get(Calendar.DATE), DAY);
        assertEquals(calendar.get(Calendar.HOUR_OF_DAY), HOUR);
        assertEquals(calendar.get(Calendar.MINUTE), MINUTE);
        assertEquals(calendar.get(Calendar.SECOND), SECOND);
        assertEquals(calendar.get(Calendar.MILLISECOND), 0);
    }

    @Test
    public void convertToLocalDateTime() {

        // 1. 参数为空时，返回当前时间
        assertNotNull(DateTimeUtils.convertToLocalDateTime(null));

        // 2. 参数不为空时
        LocalDateTime localDateTime = DateTimeUtils.convertToLocalDateTime(this.globalDate);

        assertEquals(localDateTime.toString(), this.globalLocalDateTime.toString());
    }

    @Test
    public void getStartOfDay() {
        LocalDateTime startOfDay = DateTimeUtils.getStartOfDay(this.globalLocalDateTime);

        assertEquals(startOfDay.getYear(), YEAR);
        assertEquals(startOfDay.getMonthValue(), MONTH);
        assertEquals(startOfDay.getDayOfMonth(), DAY);
        assertEquals(startOfDay.getHour(), 0);
        assertEquals(startOfDay.getMinute(), 0);
        assertEquals(startOfDay.getSecond(), 0);
    }

    @Test
    public void getEndOfDay() {
        LocalDateTime endOfDay = DateTimeUtils.getEndOfDay(this.globalLocalDateTime);

        assertEquals(endOfDay.getYear(), YEAR);
        assertEquals(endOfDay.getMonthValue(), MONTH);
        assertEquals(endOfDay.getDayOfMonth(), DAY);
        assertEquals(endOfDay.getHour(), 23);
        assertEquals(endOfDay.getMinute(), 59);
        assertEquals(endOfDay.getSecond(), 59);
    }

    @Test
    public void getLastDateOfMonth() {
        LocalDateTime lastDateOfMonth = DateTimeUtils.getLastDateOfMonth(this.globalLocalDateTime);

        assertEquals(lastDateOfMonth.getYear(), YEAR);
        assertEquals(lastDateOfMonth.getMonthValue(), MONTH);
        assertEquals(lastDateOfMonth.getDayOfMonth(), 31);
        assertEquals(lastDateOfMonth.getHour(), 0);
        assertEquals(lastDateOfMonth.getMinute(), 0);
        assertEquals(lastDateOfMonth.getSecond(), 0);
    }

    @Test
    public void getLastDayOfMonth() {
        LocalDateTime lastDayOfMonth = DateTimeUtils.getLastDayOfMonth(this.globalLocalDateTime);

        assertEquals(lastDayOfMonth.getYear(), YEAR);
        assertEquals(lastDayOfMonth.getMonthValue(), MONTH);
        assertEquals(lastDayOfMonth.getDayOfMonth(), 31);
        assertEquals(lastDayOfMonth.getHour(), 23);
        assertEquals(lastDayOfMonth.getMinute(), 59);
        assertEquals(lastDayOfMonth.getSecond(), 59);
    }

    @Test
    public void getFirstDayOfMonth() {
        LocalDateTime firstDayOfMonth = DateTimeUtils.getFirstDayOfMonth(this.globalLocalDateTime);

        assertEquals(firstDayOfMonth.getYear(), YEAR);
        assertEquals(firstDayOfMonth.getMonthValue(), MONTH);
        assertEquals(firstDayOfMonth.getDayOfMonth(), 1);
        assertEquals(firstDayOfMonth.getHour(), 0);
        assertEquals(firstDayOfMonth.getMinute(), 0);
        assertEquals(firstDayOfMonth.getSecond(), 0);
    }

    @Test
    public void format() {
        String format = DateTimeUtils.format(this.globalLocalDateTime, this.formatter);
        assertEquals(format, FORMAT_DATE + " " + FORMAT_TIME);
    }

    @Test
    public void testFormat() {
        String format = DateTimeUtils.format(this.globalLocalDateTime);
        assertEquals(format, FORMAT_DATE + " " + FORMAT_TIME);
    }
}
