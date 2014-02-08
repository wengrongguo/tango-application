package org.tango.struts.converter;

import org.apache.struts2.util.StrutsTypeConverter;
import org.tango.core.constant.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * User: tAngo
 * Date: 13-6-26
 * Time: 上午11:24
 */
public class DateConverter extends StrutsTypeConverter {

    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        Date date = null;
        String value = values[0].trim();
        String defaultTimeFormat = Constant.DEFAULT_TIME_FORMAT;
        if (value.length() != 0 && !"".equals(value)) {
            try {
                if (defaultTimeFormat.length() == value.length()){
                    date = new SimpleDateFormat(Constant.DEFAULT_TIME_FORMAT).parse(value);
                }else {
                    date = new SimpleDateFormat(Constant.DEFAULT_DATE_FORMAT).parse(value);
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return date;
    }


    @Override
    public String convertToString(Map context, Object o) {
        if (o instanceof Date) {
            return new SimpleDateFormat(Constant.DEFAULT_TIME_FORMAT).format(o);
        }
        return o.toString();
    }
}
