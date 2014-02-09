/*
 * Copyright (c) 2012. tAngo
 * 	Email : org.java.tango@gmail.com
 */

package org.tango.utils;

public class Regex {

    public final static String ALL = "[\\.\\s\\S]";
    public final static String ALL_MULTIPART = ALL.concat("+");
    public final static String REQUIRE = ".+";
    public final static String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public final static String PHONE = "^((\\(\\d{3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}$";
    public final static String MOBILE = "^((\\(\\d{3}\\))|(\\d{3}\\-))?13\\d{9}$";
    public final static String URL = "^http:\\/\\/[A-Za-z0-9]+\\.[A-Za-z0-9]+[\\/=\\?%\\-&_~`@[\\]\\':+!]*([^<>\"\"])*$";
    public final static String IdCard = "^\\d{15}(\\d{2}[A-Za-z0-9])?$";
    public final static String Number = "\\d+";
    public final static String ZIP = "[1-9]\\d{5}";
    public final static String QQ = "[1-9]\\d{4,9}";
    public final static String INTEGER = "[-\\+]?\\d+";
    public final static String Double = "[-\\+]?\\d+(\\.\\d+)?";
    public final static String English = "[A-Za-z]+";
    public final static String Chinese = "[\u0391-\uFFE5]+";
}
