package org.tango.utils.ext;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * User: tango
 * Date: 13-11-19
 * Time: 下午11:28
 */
public class StringUtils {
    /**
     * 获取汉字首字母
     *
     * @return 汉字首字母
     */
    public static String getCNFirstChar(String chineseChar) {
        return getPinYin(chineseChar).substring(0, 1);
    }

    /**
     * 获取汉字的拼音
     *
     * @param chineseChar 汉字
     * @return 拼音
     */
    public static String getPinYin(String chineseChar) {
        String character = org.tango.utils.StringUtils.EMPTY_STRING;
        char[] charArray = chineseChar.toCharArray();
        int length = charArray.length;
        String[] pyArray = new String[length];
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat pyFormart = new HanyuPinyinOutputFormat();
        pyFormart.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        pyFormart.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        pyFormart.setVCharType(HanyuPinyinVCharType.WITH_V);
        for (int i = 0; i < length; i++) {
            // 判断能否为汉字字符
            if (Character.toString(charArray[i]).matches("[^\\x00-\\xff]+")) {
                pyArray = PinyinHelper.toHanyuPinyinStringArray(charArray[i], pyFormart);
                character += pyArray[0];
            } else {
                character += Character.toString(charArray[i]);
            }
        }
        return character;
    }
}
