package org.tango.utils;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author tAngo
 */
public final class StringUtils {

    /**
     * 空字符串
     */
    public final static String EMPTY_STRING = "";

    /**
     * 字符数组 (大写字母)
     */
    public final static char[] LETTER_ARRAY_UPPER = initLetterArray('A', 'Z', 26);

    /**
     * 字符数组 (小写字母)
     */
    public final static char[] LETTER_ARRAY_LOWER = initLetterArray('a', 'z', 26);

    /**
     * 数字数组
     */
    public final static char[] NUMBER_ARRAY = initLetterArray('0', '9', 10);

    /**
     * 字符数组 (大小写字母)
     */
    public final static char[] LETTER_ARRAY_ALL = new StringBuilder().append(LETTER_ARRAY_UPPER).append(LETTER_ARRAY_LOWER).toString().toCharArray();

    /**
     * 字符数组 (大小写字母+数字)
     */
    public final static char[] LETTER_ARRAY_NUMBER_ALL = new StringBuilder().append(LETTER_ARRAY_UPPER).append(LETTER_ARRAY_LOWER).append(NUMBER_ARRAY).toString().toCharArray();

    /**
     * 获取字符数组
     *
     * @param begin  开始字符
     * @param end    结束字符
     * @param length 数组长度
     * @return 字符数组
     */
    public final static char[] initLetterArray(char begin, char end, int length) {
        char[] chars = new char[length];
        for (int i = begin, p = 0; i < end + 1 && p < length; i++, p++) {
            chars[p] = (char) i;
        }
        return chars;
    }

    /**
     * 根据长度截取中英文字符
     *
     * @param input           字符串
     * @param maxCNCharLength 最大长度(兼容中英文)
     * @return String 截取后的字符串
     */
    public static String subString(String input, int maxCNCharLength) {
        if (input == null) {
            return null;
        }
        char[] chars = input.toCharArray();
        int currentLength = 0;
        int i = 0;
        for (; i < chars.length; i++) {
            int temp = currentLength;
            boolean isChinese = Pattern.matches("[^\\x00-\\xff]+", String.valueOf(chars[i]));
            if (isChinese) {
                currentLength += 2;
            } else {
                currentLength++;
            }
            if (currentLength > maxCNCharLength) {
                currentLength = temp;
                break;
            }
        }
        return new String(chars, 0, i);
    }


    /**
     * 将中文字符串转换成ASCII码
     *
     * @param cnStr 中文字符串
     * @return ASCII码
     */
    public static String getCnASCII(String cnStr) {
        StringBuffer strBuf = new StringBuffer();
        //将字符串转换成字节序列
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            //将每个字符转换成ASCII码
            strBuf.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return strBuf.toString();
    }

    /**
     * 是否为空
     *
     * @param input 字符串
     * @return 空 = true | 非空 = false
     */
    public static boolean isNull(String input) {
        return null == input || input.trim().length() == 0;
    }

    /**
     * 是否不为空
     *
     * @param input 字符串
     * @return 非空 = true | 空 = false
     */
    public static boolean isNotNull(String input) {
        return null != input && input.trim().length() > 0;
    }

    /**
     * 空字符串返回默认值
     *
     * @param input        字符串
     * @param defaultValue 默认值
     * @return 字符串为空 = 默认值 || 字符串
     */
    public static String isNullValue(String input, String defaultValue) {
        return StringUtils.isNull(input) ? defaultValue : input;
    }

    /**
     * 拼接字符串
     *
     * @param separator 分隔符
     * @param strings   字符串数组
     * @return 拼接后的字符串
     */
    public static String join(String separator, String[] strings) {
        int length = 0;
        if (strings == null || (length = strings.length) == 0)
            return StringUtils.EMPTY_STRING;
        StringBuffer stringBuffer = new StringBuffer(length * strings[0].length()).append(strings[0]);
        for (int i = 1; i < length; i++) {
            stringBuffer.append(separator).append(strings[i]);
        }
        return stringBuffer.toString();
    }

    /**
     * 拼接字符串
     *
     * @param string    字符串
     * @param separator 分隔符
     * @param replace   替换字符串
     * @return 拼接后的字符串
     */
    public static String join(String string, String separator, String replace) {
        int length = 0;
        if (isNull(string)) {
            return StringUtils.EMPTY_STRING;
        }
        String[] strings = string.split(separator);
        StringBuffer stringBuffer = new StringBuffer((length = strings.length) * strings[0].length()).append(strings[0]);
        for (int i = 1; i < length; i++) {
            stringBuffer.append(replace).append(strings[i]);
        }
        return stringBuffer.toString();
    }

    /**
     * 获取匹配字符串数组
     *
     * @return 匹配字符串数组
     */
    public static String[] getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        List<String> results = new ArrayList<String>();
        while (matcher.find()) {
            results.add(matcher.group());
        }
        return results.toArray(new String[0]);
    }

    /**
     * 是否匹配规则
     *
     * @param regex 匹配规则[正则表达式]
     * @param input 字符串
     * @return 是否匹配
     */
    public static boolean isMatcher(String regex, String input) {
        return Pattern.matches(regex, input);
    }

    /**
     * 去除空格
     *
     * @return 去除空格后的字符串
     */
    public static final String removeSpace(String input) {
        if (input == null)
            return StringUtils.EMPTY_STRING;
        return input.replaceAll("\\s", StringUtils.EMPTY_STRING);
    }

    /**
     * 半角转全角
     *
     * @param dbcString 半角字符串
     * @return 全角字符串
     */
    public static final String DBC2SBC(String dbcString) {
        String outStr = StringUtils.EMPTY_STRING;
        String temp = StringUtils.EMPTY_STRING;
        byte[] b = null;

        for (int i = 0; i < dbcString.length(); i++) {
            try {
                temp = dbcString.substring(i, i + 1);
                b = temp.getBytes("unicode");
            } catch (java.io.UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (b[3] != -1) {
                b[2] = (byte) (b[2] - 32);
                b[3] = -1;
                try {
                    outStr = outStr + new String(b, "unicode");
                } catch (java.io.UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else
                outStr = outStr + temp;
        }
        return outStr;
    }

    /**
     * 全角转半角
     *
     * @param sbcString 全角字符串
     * @return 半角字符串
     */
    public static final String SBC2DBC(String sbcString) {
        String outStr = StringUtils.EMPTY_STRING;
        String temp = StringUtils.EMPTY_STRING;
        byte[] b = null;

        for (int i = 0; i < sbcString.length(); i++) {
            try {
                temp = sbcString.substring(i, i + 1);
                b = temp.getBytes("unicode");
            } catch (java.io.UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (b[3] == -1) {
                b[2] = (byte) (b[2] + 32);
                b[3] = 0;
                try {
                    outStr = outStr + new String(b, "unicode");
                } catch (java.io.UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else
                outStr = outStr + temp;
        }
        return outStr;
    }

    /**
     * 转全角字符串
     *
     * @param input 字符串
     * @return 半角字符串
     */
    public static String toSBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) {
                c[i] = (char) 12288;
                continue;
            }
            if (c[i] < 127)
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * 转半角字符串
     *
     * @param input 字符串
     * @return 半角字符串
     */
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /**
     * 随即字符串
     *
     * @param length 字符串长度
     * @return 随即字符串
     */
    public static String randomString(char[] scopeArray, int length) {
        Random random = new Random();
        char[] resultArray = new char[length];
        for (int i = 0; i < resultArray.length; i++) {
            resultArray[i] = scopeArray[random.nextInt(scopeArray.length)];
        }
        return String.valueOf(resultArray);
    }

    /**
     * 替换大写字符
     *
     * @param input     字符串
     * @param separator 替换符
     * @return 替换大写字符后的字符串
     */
    public static String replaceUpper(String input, String separator) {
        return input.replaceAll("([A-Z])", separator + "$1");
    }

    /**
     * 替换大写字符
     *
     * @param input     字符串
     * @param separator 替换符
     * @return 替换大写字符后的字符串
     */
    public static String replaceLower(String input, String separator) {
        return input.replaceAll("[a-z]", separator + "$1");
    }

    /**
     * 字符串去重复
     *
     * @param input 字符串
     * @return 去重复的字符串
     */
    public static String distinct(String input) {
        return input.replaceAll("(.)\\1+", "$1");
    }

    /**
     * 根据ASCII编码改变字符串
     *
     * @param input 字符串
     * @param begin ASCII开始
     * @param end   ASCII结束
     * @param value ASCII算法
     * @return 改变后的字符串
     */
    private static String changeCharValue(String input, int begin, int end, int value) {
        if (StringUtils.isNotNull(input)) {
            char[] chars = input.toCharArray();
            StringBuilder result = new StringBuilder(chars.length / 2);
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] >= begin && chars[i] <= end) {
                    result.append((char) (chars[i] + value));
                } else {
                    result.append(chars[i]);
                }
            }
            return result.toString();
        }
        return StringUtils.EMPTY_STRING;
    }

    /**
     * 替换大写字母为小写
     *
     * @param input 字符串
     * @return 替换大写字符后的字符串
     */
    public static String replaceUpper2Lower(String input) {
        return changeCharValue(input, 65, 90, 32);
    }

    /**
     * 替换小写字符为大写
     *
     * @param input 字符串
     * @return 替换大写字符后的字符串
     */
    public static String replaceLower2Upper(String input) {
        return changeCharValue(input, 97, 122, -32);
    }

    /**
     * 首字母大写
     *
     * @param input   字符串
     * @param isLower 非首字母是否小写
     * @return 转换后的字符串
     */
    public static String firstCharUpper(String input, boolean isLower) {
        if (isNull(input)) {
            return StringUtils.EMPTY_STRING;
        }
        input = input.trim();
        if (isLower) {
            input = input.toLowerCase();
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    /**
     * 首字母大写
     *
     * @param input 字符串
     * @return 首字母大写
     */
    public static String firstCharUpper(String input) {
        if (isNull(input)) {
            return StringUtils.EMPTY_STRING;
        }
        input = input.trim();
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param input 字符串
     * @return 首字母小写
     */
    public static String firstCharLower(String input) {
        if (isNull(input)) {
            return StringUtils.EMPTY_STRING;
        }
        input = input.trim();
        return input.substring(0, 1).toLowerCase() + input.substring(1);
    }

    /**
     * 绑定参数
     *
     * @param message 消息字符串
     *                (今天是?), 逐次替换'?'
     * @param params  绑定参数数组
     * @return 绑定参数后的字符串
     */
    public final static String bindParams(String message, Object... params) {
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            int indexOf = message.indexOf("?");
            if (indexOf == -1) {
                throw new RuntimeException("message set param error! can't found '?'");
            }
            message = message.substring(0, indexOf).concat(param.toString()).concat(message.substring(indexOf + 1));
        }
        return message;
    }

    /**
     * 格式化参数
     *
     * @param pattern 格式 "今天是{0}"
     * @param param   绑定参数 "星期一"
     * @return 格式化后的字符串 "今天是星期一"
     */
    public final static String format(String pattern, Object... param) {
        return new MessageFormat(pattern.replaceAll("'", "`")).format(param);
    }


    /**
     * 替换大写添加分隔符
     *
     * @param input     替换字符串 ("HelloWorld")
     * @param separator 分隔符 ("-")
     * @return 替换大写添加分隔符 (hello-world)
     */
    public final static String replaceLowerSeparator(String input, String separator) {
        input = firstCharLower(input);
        input = replaceUpper(input, separator);
        return input.toLowerCase();
    }
}
