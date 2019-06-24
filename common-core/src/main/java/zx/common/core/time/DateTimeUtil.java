package zx.common.core.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.Objects;

/**
 * date time 工具类
 *
 * @author zhangxin
 */
public class DateTimeUtil {
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * {@link LocalDateTime} 转成 {@link Date}
     *
     * @param localDateTime {@link LocalDateTime} 格式的时间
     * @return {@link Date} 格式的时间
     */
    public static Date getDate(LocalDateTime localDateTime) {
        if (Objects.isNull(localDateTime)) {
            return new Date();
        }

        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * {@link Date} 转成 {@link LocalDateTime}
     *
     * @param date {@link Date} 格式的时间
     * @return {@link LocalDateTime} 格式的时间
     */
    public static LocalDateTime getLocalDateTime(Date date) {
        if (Objects.isNull(date)) {
            return LocalDateTime.now();
        }

        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
    }

    /**
     * 获取一天的开始时间 yyyy-MM-dd 00:00:00
     *
     * @param localDateTime 指定的时间
     * @return 指定时间所在当天的开始时间
     */
    public static LocalDateTime getStartOfDay(LocalDateTime localDateTime) {
        return orElseGetNow(localDateTime).with(LocalTime.MIN);
    }

    /**
     * 获取一天的结束时间 yyyy-MM-dd 23:59:59
     *
     * @param localDateTime 指定的时间
     * @return 指定时间所在当天的结束时间
     */
    public static LocalDateTime getEndOfDay(LocalDateTime localDateTime) {
        return orElseGetNow(localDateTime).with(LocalTime.MAX).withNano(0);
    }

    /**
     * 获取指定日期所在月份的最后一天
     * <p>
     * TIP: time format: 00:00:00
     *
     * @param localDateTime 指定的日期
     * @return 转换后的日期
     */
    public static LocalDateTime getLastDateOfMonth(LocalDateTime localDateTime) {
        return orElseGetNow(localDateTime).with(TemporalAdjusters.lastDayOfMonth()).withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * 获取指定日期所在月份的最后一天
     * <p>
     * TIP: time format: 23:59:59
     *
     * @param localDateTime 指定的日期
     * @return 指定日期所在月份的最后一天
     */
    public static LocalDateTime getLastDayOfMonth(LocalDateTime localDateTime) {
        // 当毫秒大于 499 时，插入数据库（datetime 类型）会进位，这里把毫秒位去掉
        return orElseGetNow(localDateTime).with(TemporalAdjusters.lastDayOfMonth()).with(LocalTime.MAX).withNano(0);
    }

    /**
     * 获取指定日期所在月份的第一天
     * <p>
     * TIP: time format: 00:00:00
     *
     * @param localDateTime 指定的日期
     * @return 指定日期所在月份的第一天
     */
    public static LocalDateTime getFirstDayOfMonth(LocalDateTime localDateTime) {
        return orElseGetNow(localDateTime).with(TemporalAdjusters.firstDayOfMonth()).with(LocalTime.MIN);
    }

    /**
     * 如果入参是 null 则返回当前时间，否则返回入参时间
     *
     * @param localDateTime 入参时间
     * @return 如果入参是 null 则返回当前时间，否则返回入参时间
     */
    private static LocalDateTime orElseGetNow(LocalDateTime localDateTime) {
        return Objects.isNull(localDateTime) ? LocalDateTime.now() : localDateTime;
    }

    /**
     * 格式化时间
     *
     * @param localDateTime     时间
     * @param dateTimeFormatter 格式化的格式
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime localDateTime, DateTimeFormatter dateTimeFormatter) {
        LocalDateTime dateTime = orElseGetNow(localDateTime);
        DateTimeFormatter formatter = Objects.isNull(dateTimeFormatter) ? DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT) : dateTimeFormatter;

        return dateTime.format(formatter);
    }

    /**
     * 格式化时间
     * <p>
     * TIP: 时间格式为：yyyy-MM-dd HH:mm:ss
     *
     * @param localDateTime 时间
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime localDateTime) {
        return format(localDateTime, null);
    }
}
