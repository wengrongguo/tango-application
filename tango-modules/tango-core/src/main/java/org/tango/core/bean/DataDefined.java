package org.tango.core.bean;

import java.util.Map;

/**
 * User: tango
 * Date: 13-12-7
 * Time: 下午5:59
 */
public interface DataDefined<T> {
    Map<String, Object> defined(T o);
}
