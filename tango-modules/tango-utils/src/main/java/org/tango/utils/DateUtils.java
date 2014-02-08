/*
 * Copyright (c) 2012. tAngo
 * 	Email : org.java.tango@gmail.com
 */

package org.tango.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author tAngo
 * @since 1.5+
 */
public final class DateUtils {

    public static final DateUtils INSTANCE = new DateUtils();
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    /***************************** parse *******************************/

    /**
     * 字符串转换日期+时间
     */
    public static Date parseDateTimeByString(String dateString) {
        return parseDateByString(dateString, DEFAULT_DATETIME_FORMAT, new ParsePosition(0));
    }

    /**
     * 字符串转换日期+时间
     */
    public static Date parseDateByString(String dateString) {
        return parseDateByString(dateString, DEFAULT_DATE_FORMAT, new ParsePosition(0));
    }

    /**
     * 字符串转换日期
     *
     * @param dateString 日期字符串
     * @param dateFormat 日期格式
     * @return 日期
     */
    public static Date parseDateByString(String dateString, String dateFormat) {
        return parseDateByString(dateString, dateFormat, new ParsePosition(0));
    }

    /**
     * 字符串转换日期
     *
     * @param dateString    日期字符串
     * @param dateFormat    日期格式
     * @param parsePosition 跟踪当前位置
     * @return 日期
     */
    public static Date parseDateByString(String dateString, String dateFormat, ParsePosition parsePosition) {
        try {
            return new SimpleDateFormat(dateFormat).parse(dateString, parsePosition);
        } catch (Exception e) {
            return null;
        }
    }

    public static Timestamp parseTimestampByString(String datetime) {
        Date date = parseDateTimeByString(datetime);
        if (date != null) {
            return new Timestamp(date.getTime());
        }
        return null;
    }

    public static java.sql.Date parseSqlDateByString(String sqlDate) {
        Date date = parseDateByString(sqlDate);
        if (date != null) {
            return new java.sql.Date(date.getTime());
        }
        return null;
    }

    /***************************** getter *******************************/
    /**
     * 返回当前年
     *
     * @return 当前年
     */
    public int getYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * 返回当前月
     *
     * @return 当前月
     */
    public int getMouth() {
        return Calendar.getInstance().get(Calendar.MONTH);
    }

    /**
     * 返回当前日
     *
     * @return 当前日
     */
    public int getDay() {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 返回当前小时(12小时制)
     *
     * @return 当前小时
     */
    public int getHour12() {
        return Calendar.getInstance().get(Calendar.HOUR);
    }

    /**
     * 返回当前小时(24小时制)
     *
     * @return 当前小时
     */
    public int getHour24() {
        return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 返回日期差值
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 日期差值
     * @throws java.text.ParseException
     */
    public int getDiffDate(String date1, String date2) throws ParseException {
        return this.getDiffHour(date1, date2) / 24;
    }

    /**
     * 返回小时差值
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 日期差值
     * @throws java.text.ParseException
     */
    public int getDiffHour(String date1, String date2) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        long date1Long = dateFormat.parse(date1).getTime();
        long date2Long = dateFormat.parse(date2).getTime();
        long diffDay = (date1Long - date2Long) / (60 * 60 * 1000);
        return (int) diffDay;
    }

    /**
     * 返回当前时间戳
     *
     * @return 当前时间戳
     */
    public static Timestamp getDateNowTimestamp() {
        Date date = new Date();
        return new Timestamp(date.getTime());
    }

    /**
     * 返回当前时间戳
     *
     * @return 当前时间戳
     */
    public static Date getDateNow() {
        return new Date();
    }

    /**
     * 返回当前时间
     *
     * @return 当前时间
     */
    public static java.sql.Date getDateNowSql() {
        return new java.sql.Date(new Date().getTime());
    }

    /**
     * 返回默认格式的当前日期字符串
     *
     * @return 当前日期字符串
     */
    public String getDateNowDefaultFormat() {
        Date date = new Date();
        return format(date, DateUtils.DEFAULT_DATE_FORMAT);
    }

    /**
     * 返回默认格式的日期字符串
     *
     * @param date 日期对象,data == null,为当前日期
     * @return 日期字符串
     */
    public static String format(Date date) {
        if (date == null)
            date = new Date();
        return format(date, DateUtils.DEFAULT_DATE_FORMAT);
    }

    /**
     * 返回指定格式的日期字符串
     *
     * @param date             日期
     * @param dateFormatString 日期格式
     * @return 日期字符串
     */
    public static String format(Date date, String dateFormatString) {
        return new SimpleDateFormat(dateFormatString).format(date);
    }

    /**
     * 日期对比
     *
     * @param x 日期1
     * @param y 日期2
     * @return 如 日期1 在 日期2之前,返回 true ,否则返回 false
     */
    public boolean compare(Calendar x, Calendar y) {
        return x.before(y);
    }

    /**
     * 日期对比
     *
     * @param x 日期1
     * @param y 日期2
     * @return 如 日期1 在 日期2之前,返回 true ,否则返回 false
     */
    public boolean compare(Date x, Date y) {
        return x.before(y);
    }

    /**
     * 日期对比
     *
     * @param x 日期1
     * @param y 日期2
     * @return 如 日期1 在 日期2之前,返回 true ,否则返回 false
     */
    public boolean compare(String x, String y) {
        Date a = parseDateByString(x);
        Date b = parseDateByString(y);
        return a.before(b);
    }

    /**
     * 日期对比
     *
     * @param x            日期1
     * @param y            日期2
     * @param formatString 日期格式
     * @return 如 日期1 在 日期2之前,返回 true ,否则返回 false
     */
    public boolean compare(String x, String y, String formatString) {
        Date a = parseDateByString(x, formatString);
        Date b = parseDateByString(y, formatString);
        return a.before(b);
    }
}
