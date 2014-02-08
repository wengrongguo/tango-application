package org.tango.core.abs;

import org.tango.core.bean.Pager;
import org.tango.core.bean.QueryMapping;

import java.io.Serializable;
import java.util.List;

public interface AbstractService<T extends AbstractModel> {

    public abstract boolean save(T entry);

    public abstract T get(Integer id);

    public abstract boolean update(T entry);

    public abstract boolean updateNotNull(T entry);

    public abstract boolean delete(Integer id);

    public boolean deletes(List<Integer> ids);

    public abstract List<T> getList(QueryMapping queryMapping);

    public abstract Pager<T> getListPage(Pager<T> pager, QueryMapping queryMapping);

    public abstract int count(QueryMapping queryMapping);

}
