/*
 * Copyright (c) 2012. tAngo
 * 	Email : org.java.tango@gmail.com
 */

package org.tango.utils;

import java.nio.charset.Charset;

/**
 * 编码类
 * User: tAngo
 * Date: 12-12-10
 * Time: 下午10:36
 */
public final class Encode {

    public static class CharacterSet {
        /**
         * 8 位 UCS 转换格式
         */
        public static final Charset UTF8 = Charset.forName("UTF-8");
        /**
         * ISO 拉丁字母表 No.1，也叫作 ISO-LATIN-1
         */
        public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
        /**
         * 7 位 ASCII 字符，也叫作 ISO646-US、Unicode 字符集的基本拉丁块
         */
        public static final Charset US_ASCII = Charset.forName("US-ASCII");
        /**
         * 16 位 UCS 转换格式，Big Endian（最低地址存放高位字节）字节顺序
         */
        public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
        /**
         * 16 位 UCS 转换格式，Little-Endian（最高地址存放低位字节）字节顺序
         */
        public static final Charset UTF_16LE = Charset.forName("UTF-16BE");
        /**
         * 16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识
         */
        public static final Charset UTF_16 = Charset.forName("UTF-16");
    }

}
