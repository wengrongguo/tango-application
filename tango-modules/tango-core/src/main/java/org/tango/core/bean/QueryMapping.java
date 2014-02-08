package org.tango.core.bean;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: tAngo
 * Date: 12-12-26
 * Time: 下午7:04
 */
public class QueryMapping {

    private List<Map.Entry<String, Object>> equals = null;

    private List<Map.Entry<String, Object>> gts = null;

    private List<Map.Entry<String, Object>> lts = null;

    private List<Map.Entry<String, Object>> likes = null;

    private List<Map.Entry<String, Object[]>> between = null;

    private List<Map.Entry<String, Object[]>> ins = null;

    private List<Map.Entry<String, Boolean>> orders = null;

    public QueryMapping() {
    }

    public QueryMapping addEquals(String prop, Object value) {
        if (equals == null) {
            equals = new ArrayList<Map.Entry<String, Object>>();
        }
        equals.add(new AbstractMap.SimpleEntry<String, Object>(prop, value));
        return this;
    }

    public QueryMapping addLike(String prop, Object value) {
        if (likes == null) {
            likes = new ArrayList<Map.Entry<String, Object>>();
        }
        likes.add(new AbstractMap.SimpleEntry<String, Object>(prop, value));
        return this;
    }

    public QueryMapping addGt(String prop, Object value) {
        if (gts == null) {
            gts = new ArrayList<Map.Entry<String, Object>>();
        }
        gts.add(new AbstractMap.SimpleEntry<String, Object>(prop, value));
        return this;
    }

    public QueryMapping addLt(String prop, Object value) {
        if (lts == null) {
            lts = new ArrayList<Map.Entry<String, Object>>();
        }
        lts.add(new AbstractMap.SimpleEntry<String, Object>(prop, value));
        return this;
    }

    public QueryMapping addOrder(String prop, Boolean isAsc) {
        if (orders == null) {
            orders = new ArrayList<Map.Entry<String, Boolean>>();
        }
        orders.add(new AbstractMap.SimpleEntry<String, Boolean>(prop, isAsc));
        return this;
    }

    public QueryMapping addBetween(String prop, Object[] area) {
        if (between == null) {
            between = new ArrayList<Map.Entry<String, Object[]>>();
        }
        between.add(new AbstractMap.SimpleEntry<String, Object[]>(prop, area));
        return this;
    }

    public QueryMapping addIn(String prop, Object[] ins) {
        if (this.ins == null) {
            this.ins = new ArrayList<Map.Entry<String, Object[]>>();
        }
        this.ins.add(new AbstractMap.SimpleEntry<String, Object[]>(prop, ins));
        return this;
    }

    public List<Map.Entry<String, Object>> getEquals() {
        return equals;
    }

    public List<Map.Entry<String, Object>> getGts() {
        return gts;
    }

    public List<Map.Entry<String, Object>> getLts() {
        return lts;
    }

    public List<Map.Entry<String, Object>> getLikes() {
        return likes;
    }

    public List<Map.Entry<String, Object[]>> getBetween() {
        return between;
    }

    public List<Map.Entry<String, Boolean>> getOrders() {
        return orders;
    }

    public List<Map.Entry<String, Object[]>> getIns() {
        return ins;
    }
}
