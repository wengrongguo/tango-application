/*
 * Copyright (c) 2012. tAngo
 * 	Email : org.java.tango@gmail.com
 */

package org.tango.utils;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

/**
 * 转换工具类
 *
 * @author tAngo
 * @since 1.5+
 */
public final class Converters {


    /**
     * 字符串转整型
     *
     * @param input 字符串
     * @return 转换后的整型, 如转换异常, 则返回0
     */
    public final static int string2Int(String input) {
        int result = 0;
        try {
            result = Integer.parseInt(input);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 字符串转整型
     *
     * @param input          字符串
     * @param exceptionValue 异常的默认值
     * @return 转换后的整型, 如转换异常, 则返回 exceptionValue
     */
    public final static int string2Int(String input, int exceptionValue) {
        int result = exceptionValue;
        try {
            result = Integer.parseInt(input);
        } catch (Exception e) {
        }
        return result;
    }


    /**
     * 字符串转短整型
     *
     * @param input 字符串
     * @return 短整型
     */
    public final static short string2Short(String input) {
        short result = 0;
        try {
            result = Short.parseShort(input);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 字符串转短整型
     *
     * @param input          字符串
     * @param exceptionValue 异常时的默认值
     * @return 转换后的短整型, 如转换异常, 则返回 exceptionValue
     */
    public final static short string2Short(String input, short exceptionValue) {
        short result = exceptionValue;
        try {
            result = Short.parseShort(input);
        } catch (Exception e) {
        }
        return result;
    }


    /**
     * 字符串转整型(16进制)
     *
     * @param input 字符串
     * @return
     */
    public final static int hexString2Int(String input) {
        int result = 0;
        try {
            result = Integer.parseInt(input, 16);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 字符串转数值类型
     *
     * @param input 字符串
     * @param clazz 需要转换的类型,必须为包装类,如: Integer.class
     * @return 数值对象
     */
    public final static <T> T string2Number(String input, Class<T> clazz) {
        T result = null;
        try {
            Constructor<T> constructor = clazz.getConstructor(input.getClass());
            result = constructor.newInstance(input);
        } catch (Exception e) {
        }
        return (T) result;
    }

    /**
     * 字符串转布尔
     *
     * @param input 字符串
     * @return 布尔值, 如 input=="true",返回true,否则返回false
     */
    public final static boolean string2Boolean(String input) {
        if (input == null || input.trim().length() == 0) {
            return false;
        }
        return "true".equals(input.trim());
    }

    /**
     * 字符串转枚举
     *
     * @param input     字符串
     * @param enumClass 枚举类型 如: Color.class
     * @return 枚举
     */
    public final static <T extends Enum<T>> T string2Enum(String input, Class<T> enumClass) {
        return Enum.valueOf(enumClass, input);
    }

    public final static Integer[] intArr2IntegerArr(int... ins) {
        Integer[] integers = new Integer[ins.length];
        for (int i = 0; i < ins.length; i++) {
            integers[i] = ins[i];
        }
        return integers;
    }

    /**
     * 字符转整型
     *
     * @param input 字符值
     * @return char整型值, 对应的ASCII码
     */
    public final static int char2Int(char input) {
        return (int) input;
    }

    /**
     * 字符转布尔
     *
     * @param input 字符(1 || -1)
     * @return 布尔值
     */
    public final static boolean char2Boolean(char input) {
        return input == '1' ? true : false;
    }

    /**
     * 字符转字节数组
     *
     * @param c 字符
     * @return 字节数组
     */
    public final static byte[] charToByte(char c) {
        byte[] b = new byte[2];
        b[0] = (byte) ((c & 0xFF00) >> 8);
        b[1] = (byte) (c & 0xFF);
        return b;
    }

    /**
     * 布尔转字符串
     *
     * @param bool 布尔值
     * @return 字符串, 如 bool == true ,返回"true",否则返回"false"
     */
    public final static String boolean2String(boolean bool) {
        return bool ? "true" : "false";
    }

    /**
     * 布尔转整型
     *
     * @param bool 布尔值
     * @return 整数, 如 bool == true ,返回 1,否则返回 -1
     */
    public final static int boolean2Int(boolean bool) {
        return bool ? 1 : -1;
    }

    /**
     * 整型转布尔
     *
     * @param num 整数
     * @return 布尔值, 如 num为大于0的任何数,返回true,否则返回false
     */
    public final static boolean int2Boolean(int num) {
        return num >= 1 ? true : false;
    }

    /**
     *
     */
    public static char byteToChar(byte[] b) {
        return (char) (((b[0] & 0xFF) << 8) | (b[1] & 0xFF));
    }

    /**
     * 对象转Map
     *
     * @param object 对象
     * @return 转换后的Map
     */
    public static final Map<String, Object> object2Map(Object object, Class<?>... noConClasses) {
        Map<String, Object> returnMap = new HashMap<String, Object>(0);
        Map<Class<?>, Class<?>> noConClassesMap = new HashMap<Class<?>, Class<?>>(noConClasses.length);
        for (int i = 0; i < noConClasses.length; i++) {
            noConClassesMap.put(noConClasses[i], noConClasses[i]);
        }
        int noConClassesLength = noConClassesMap.size();
        try {
            if (object != null) {
                PropertyDescriptor[] descriptors = Introspector.getBeanInfo(object.getClass()).getPropertyDescriptors();
                for (PropertyDescriptor propertyDescriptor : descriptors) {
                    Method getMethod = propertyDescriptor.getReadMethod();
                    Class<?> returnType = getMethod.getReturnType();
                    if (noConClassesLength > 0 && noConClassesMap.containsKey(returnType)) {
                        continue;
                    } else {
                        if (returnType.isPrimitive() || returnType == String.class//
                                || returnType == Integer.class || returnType == Short.class//
                                || returnType == Date.class || returnType == Timestamp.class) {
                            Object returnValue = getMethod.invoke(object, (Object[]) null);
                            if (returnValue != null && !returnValue.equals(""))
                                returnMap.put(propertyDescriptor.getName(), returnValue);
                        }
                    }
                }
            }
        } catch (Exception e) {
            new RuntimeException(e);
        }
        return returnMap;
    }

    /**
     * 对象转对象属性数组
     *
     * @param object 转换对象
     * @return 对象属性数组
     */
    public static final Object[] object2PropertyArray(Object object) {
        return object2PropertyArray(object, 0);
    }

    /**
     * 对象转对象属性数组
     *
     * @param object 转换对象
     * @param doNull 是否可为空
     * @return 对象属性数组
     */
    public static final Object[] object2PropertyArray(Object object, boolean doNull) {
        Object[] propertyArray = object2PropertyArray(object, 0);
        if (doNull) {
            return propertyArray;
        } else {
            List<Object> list = new ArrayList<Object>();
            for (int i = 0; i < propertyArray.length; i++) {
                Object o = propertyArray[i];
                if (o != null) {
                    list.add(o);
                }
            }
            return list.toArray();
        }
    }


    /**
     * 对象转对象属性数组
     *
     * @param object     转换对象
     * @param startIndex 属性数组起始位置
     * @return 对象属性数组
     */
    public static final Object[] object2PropertyArray(Object object, int startIndex) {
        if (object == null) {
            throw new IllegalArgumentException("object is null");
        }
        Object[] property = null;
        try {
            Class<?> resourceClass = object.getClass();
            Field[] fields = resourceClass.getDeclaredFields();
            property = new Object[fields.length - startIndex];
            for (int i = 0; i < property.length; i++) {
                Field field = fields[startIndex++];
                Object returnValue = resourceClass.getMethod("get".concat(StringUtils.firstCharUpper(field.getName()))).invoke(object, (Object[]) null);
                property[i] = returnValue;
            }
        } catch (Exception e) {
            new RuntimeException(e);
        }
        return property;
    }

    /**
     * 字符串转整形数组
     *
     * @param ids   字符串
     * @param split 分割字符串
     * @return 整形数组
     */
    public static int[] string2IntArray(String ids, String split) {
        if (StringUtils.isNull(ids))
            return new int[0];
        String[] idString = ids.split(split);
        int[] is = new int[idString.length];
        for (int i = 0; i < idString.length; i++) {
            is[i] = Integer.parseInt(idString[i].trim());
        }
        return is;
    }

    /**
     * 字符串转整形数组
     *
     * @param ids 字符串
     * @return 整形数组
     */
    public static int[] string2IntArray(String ids) {
        return string2IntArray(ids, ",");
    }

    /**
     * 集合转字符串
     *
     * @param list 集合
     * @return 字符串
     */
    public static <T> String list2String(List<T> list) {
        return Arrays.toString(list.toArray()).replaceAll("[\\[\\]]", StringUtils.EMPTY_STRING);
    }
}
