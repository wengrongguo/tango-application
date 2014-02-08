/*
 * Copyright (c) 2012. tAngo
 * 	Email : org.java.tango@gmail.com
 */
package org.tango.core.abs;

import org.tango.core.bean.Pager;
import org.tango.core.bean.QueryMapping;

import java.io.Serializable;
import java.util.List;

/**
 * 抽象Dao-Hibernate
 * User: tAngo
 * Date: 12-11-11
 * Time: 下午4:49
 */
public interface AbstractDao<T> {

    public abstract boolean save(T entity);

    public abstract void save(T... entity);

    public abstract boolean delete(Integer serializableId);

    public abstract boolean delete(T entity);

    public abstract boolean deletes(List<Integer> ids);

    public abstract boolean deletes(QueryMapping queryMapping);

    public abstract boolean update(T entity);

    public abstract boolean updateNotNull(T entity);

    public abstract T get(Integer serializableId);

    public abstract List<T> get(int[] serializableIds);

    public abstract List<T> getList(QueryMapping queryMapping);

    public abstract Pager<T> getListPage(Pager<T> pager, QueryMapping queryMapping);

    public abstract int count(QueryMapping queryMapping);
}
