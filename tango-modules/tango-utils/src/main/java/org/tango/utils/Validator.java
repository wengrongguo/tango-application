package org.tango.utils;

public class Validator {

    public static boolean isNull(String checkValue) {
        return StringUtils.isNull(checkValue);
    }

    public static boolean isDate(String checkValue) {
        return !StringUtils.isNull(checkValue) && DateUtils.parseDateByString(checkValue) != null;
    }

    public static boolean isNaN(String checkValue) {
        return !StringUtils.isNull(checkValue) && !checkValue.matches(Regex.INTEGER);
    }

    public static boolean isNumber(String checkValue) {
        return !StringUtils.isNull(checkValue) && checkValue.matches(Regex.INTEGER);
    }

    public static boolean isUnSignNumber(String checkValue) {
        return !StringUtils.isNull(checkValue) && checkValue.matches(Regex.Number);
    }

    public static boolean isEmail(String checkValue) {
        return !StringUtils.isNull(checkValue) && checkValue.matches(Regex.EMAIL);
    }

    public static boolean isPhone(String checkValue) {
        return !StringUtils.isNull(checkValue) && checkValue.matches(Regex.PHONE);
    }

    public static boolean isMobile(String checkValue) {
        return !StringUtils.isNull(checkValue) && checkValue.matches(Regex.MOBILE);
    }

    public static boolean isURL(String checkValue) {
        return !StringUtils.isNull(checkValue) && checkValue.matches(Regex.URL);
    }

    public static boolean isQQ(String checkValue) {
        return !StringUtils.isNull(checkValue) && checkValue.matches(Regex.QQ);
    }

    public static boolean isZIP(String checkValue) {
        return !StringUtils.isNull(checkValue) && checkValue.matches(Regex.ZIP);
    }

    public static boolean isDouble(String checkValue) {
        return !StringUtils.isNull(checkValue) && checkValue.matches(Regex.Double);
    }

    public static boolean isEnglish(String checkValue) {
        return !StringUtils.isNull(checkValue) && checkValue.matches(Regex.English);
    }

    public static boolean isChinese(String checkValue) {
        return !StringUtils.isNull(checkValue) && checkValue.matches(Regex.Chinese);
    }

    public static boolean vStrLength(String checkValue, int minLength, int maxLength) {
        if (StringUtils.isNull(checkValue)) {
            return false;
        }
        int length = checkValue.trim().length();
        return length >= minLength && length <= maxLength;
    }

    public static boolean vNumLength(String checkValue, int minLength, int maxLength) {
        if (!isNumber(checkValue)) {
            return false;
        }
        long number = Long.parseLong(checkValue);
        long length = number / 10;
        return length >= minLength && length <= maxLength;
    }
}
