package org.tango.struts.converter;

import org.apache.struts2.util.StrutsTypeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * User: tAngo
 * Date: 13-6-26
 * Time: 上午11:24
 */
public class ListConverter extends StrutsTypeConverter {


    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        List<Integer> integers = new ArrayList<Integer>();
        String value = values[0].trim();
        if (value.length() != 0 && !"".equals(value)) {
            String[] arr = value.split(",");
            for (int i = 0; i < arr.length; i++) {
                integers.add(Integer.parseInt(arr[i]));
            }
        }
        return integers;
    }


    @Override
    public String convertToString(Map context, Object o) {
        return null;
    }
}
