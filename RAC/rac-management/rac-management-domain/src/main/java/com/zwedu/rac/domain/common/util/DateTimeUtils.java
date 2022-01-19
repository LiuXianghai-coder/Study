package com.zwedu.rac.domain.common.util;

import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 时间相关工具类
 *
 * @author qingchuan
 * @date 2020/12/9
 */
@Slf4j
public class DateTimeUtils {

    /**
     * 获得两个时间的差值
     *
     * @param from         开始时间
     * @param to           结束时间
     * @param dateTimeEnum 时间类型
     * @return
     */
    public static long getDuration(LocalDateTime from, LocalDateTime to, DateTimeEnum dateTimeEnum) {
        checkNotNull(from);
        checkNotNull(to);
        checkNotNull(dateTimeEnum);

        Duration duration = Duration.between(from, to);

        switch (dateTimeEnum) {
            case YEAR:
                return ChronoUnit.YEARS.between(from, to);
            case MONTH:
                return ChronoUnit.MONTHS.between(from, to);
            case DAY:
                return duration.toDays();
            case HOUR:
                return duration.toHours();
            case MINUTE:
                return duration.toMinutes();
            case SECOND:
                return duration.getSeconds();
            case MILLI:
                return duration.toMillis();
            case NANO:
                return duration.toNanos();
            default:
                throw new IllegalArgumentException("不支持的类型");
        }
    }

    /**
     * date to LocalDate
     *
     * @param dateToConvert 待转换日期
     * @return LocalDate
     */
    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return checkNotNull(dateToConvert).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    /**
     * timeMillis to LocalDate
     *
     * @param timeMillis measured in milliseconds
     * @return LocalDate
     */
    public static LocalDate convertToLocalDate(long timeMillis) {
        return Instant.ofEpochMilli(timeMillis).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * date to LocalDateTime
     *
     * @param dateToConvert 待转换时间
     * @return LocalDateTime
     */
    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return checkNotNull(dateToConvert).toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * timeMillis to LocalDateTime
     *
     * @param timeMillis measured in milliseconds
     * @return LocalDateTime
     */
    public static LocalDateTime convertToLocalDateTime(long timeMillis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMillis), ZoneId.systemDefault());
    }

    /**
     * LocalDate to Date
     *
     * @param dateToConvert 待转换日期
     * @return Date
     */
    public static Date convertToDate(LocalDate dateToConvert) {
        return Date.from(checkNotNull(dateToConvert).atStartOfDay().atZone(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * LocalDate to Date
     *
     * @param dateToConvert 待转换日期
     * @return Date
     */
    public static Date convertToDate(LocalDateTime dateToConvert) {
        return Date.from(checkNotNull(dateToConvert).atZone(ZoneId.systemDefault())
                .toInstant());
    }

    /**
     * timeMillis to LocalDate
     *
     * @param localDateTime 待转换时间
     * @return timeMillis
     */
    public static long convertToTimeMillis(LocalDateTime localDateTime) {
        return checkNotNull(localDateTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * timeMillis to LocalDate
     *
     * @param localDate 待转换时间
     * @return timeMillis
     */
    public static long convertToTimeMillis(LocalDate localDate) {
        return checkNotNull(localDate).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 判断在某个时间内 [startTime, endTime]
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param date      指定时间
     * @return true 在时间区间内
     */
    public static boolean between(Date startTime, Date endTime, Date date) {
        if (checkNotNull(date).getTime() == checkNotNull(startTime).getTime()
                || date.getTime() == checkNotNull(endTime).getTime()) {
            return true;
        }

        return date.after(startTime) && date.before(endTime);
    }
}
