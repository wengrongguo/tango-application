/*
 * Copyright (c) 2012. tAngo
 * 	Email : org.java.tango@gmail.com
 */
package org.tango.utils;

import java.lang.reflect.Array;
import java.util.*;

/**
 * 数组工具类
 *
 * @author tAngo
 * @since 1.5+
 */
public final class ArrayUtils {

    /**
     * 返回下标
     *
     * @param objArray 对象数组
     * @param object   对象
     * @return 下标
     */
    public final static int indexOf(Object[] objArray, Object object) {
        for (int i = 0; i < objArray.length; i++) {
            if (objArray[i].equals(object))
                return i;
        }
        return -1;
    }

    /**
     * 返回字符串数组
     *
     * @param objArray
     * @return 字符串数组
     */
    public final static String[] toStringArray(Object[] objArray) {
        int length = objArray.length;
        String[] result = new String[length];
        for (int i = 0; i < length; i++) {
            result[i] = objArray[i].toString();
        }
        return result;
    }

    /**
     * 数组去重复(对象)
     *
     * @param objArray
     * @return 对象数组
     */
    public final static <T> T[] distinctArray(T[] objArray) {
        Set<T> container = new HashSet<T>(Arrays.asList(objArray));
        return (T[]) container.toArray();
    }

    /**
     * 将指定的值分配给指定数组的每个元素
     *
     * @param value  字符串
     * @param length 长度
     * @return 字符数组
     */
    public final static String[] fillArray(String value, int length) {
        String[] result = new String[length];
        Arrays.fill(result, value);
        return result;
    }

    /**
     * 集合转字符串数组
     *
     * @param coll 集合
     * @return 字符串数组
     */
    public final static String[] toStringArray(Collection<?> coll) {
        Object[] objArray = coll.toArray();
        String[] strArray = new String[objArray.length];
        for (int i = 0; i < objArray.length; i++) {
            strArray[i] = objArray.toString();
        }
        return strArray;
    }

    /**
     * 对象数组类型转换
     *
     * @param array     原对象数组
     * @param toObjects 转换后的对象数组
     * @return 转换后的对象数组
     */
    public static Object[] typecast(Object[] array, Object[] toObjects) {
        return Arrays.asList(array).toArray(toObjects);
    }

    /**
     * 数组,集合转List
     *
     * @param array 数组或集合
     * @return ArrayList
     */
    public final static List<?> toList(Object array) {
        if (array instanceof Object[])
            return Arrays.asList((Object[]) array);
        int size = Array.getLength(array);
        ArrayList<Object> list = new ArrayList<Object>(size);
        for (int i = 0; i < size; i++) {
            list.add(Array.get(array, i));
        }
        return list;
    }

    /**
     * 数组,集合转List
     *
     * @param array 数组或集合
     * @return ArrayList
     */
    public final static <T> List<T> toList(Object array, Class<T> clazz) {
        if (array instanceof Object[])
            return Arrays.asList((T[]) array);
        int size = Array.getLength(array);
        ArrayList<T> list = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
            list.add((T) Array.get(array, i));
        }
        return list;
    }

    /**
     * 迭代器转List
     *
     * @param iterator
     * @return ArrayList
     */
    public final static List<?> toList(Iterator<?> iterator) {
        List<Object> list = new ArrayList<Object>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    /**
     * 是否为非空数组
     *
     * @param array 数组
     * @return 是否为非空数组
     */
    public static boolean isNotNull(Object[] array) {
        return (array != null);
    }

    /**
     * 是否为空数组
     *
     * @param array 数组
     * @return 是否为空数组
     */
    public static boolean isNull(Object[] array) {
        return (array == null);
    }


    /**
     * 是否不为空
     *
     * @param array 数组
     * @return 不为空且长度 > 0
     */
    public static boolean isNotEmpty(Object[] array) {
        return isNotNull(array) && array.length > 0;
    }

    /**
     * 是否为空
     *
     * @param array 数组
     * @return 不为空且长度 = 0
     */
    public static boolean isEmpty(Object[] array) {
        return isNotNull(array) && array.length == 0;
    }

    /**
     * 合并数组
     *
     * @return 合并后的数组
     */
    public static Object[] concat(Object[] objArray1, Object[] objArray2) {
        Object[] result = new Object[objArray1.length + objArray2.length];
        System.arraycopy(objArray1, 0, result, 0, objArray1.length);
        System.arraycopy(objArray2, 0, result, objArray1.length, objArray2.length);
        return result;
    }

    /**
     * 对象数组转Class数组
     *
     * @param objects 对象数组
     * @return Class数组
     */
    public final static Class<?>[] parseClassArray(Object... objects) {
        Class<?>[] classes = new Class<?>[objects.length];
        for (int i = 0; i < classes.length; i++) {
            classes[i] = objects[i].getClass();
        }
        return classes;
    }

    /**
     * Array.join方法(连接数组分割字符串)
     *
     * @param objects 对象数组
     * @param s       连接字符串
     * @return 连接后的字符串
     */
    public final static String join(Object[] objects, String s) {
        StringBuilder stringBuilder = new StringBuilder();
        if (objects.length > 0) {
            stringBuilder.append(objects[0].toString());
            for (int i = 1; i < objects.length; i++) {
                stringBuilder.append(s).append(objects[0].toString());
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 数组交换
     *
     * @param objects  数组
     * @param srcIndex 原索引
     * @param tarIndex 目标索引
     * @return 交换后的数组
     */
    public final static Object[] swap(Object[] objects, int srcIndex, int tarIndex) {
        Object tempObject = objects[srcIndex];
        objects[srcIndex] = objects[tarIndex];
        objects[tarIndex] = tempObject;
        return objects;
    }
}
