package org.tango.framework.service.impl;

import org.tango.core.abs.AbstractModel;
import org.tango.core.abs.AbstractService;
import org.tango.core.bean.Pager;
import org.tango.core.bean.QueryMapping;
import org.tango.framework.dao.impl.FrameworkDaoSupport;
import org.tango.framework.domain.Role;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: tango
 * Date: 13-11-17
 * Time: 下午9:33
 */
public class FrameworkServiceSupport<T extends AbstractModel> extends FrameworkDaoSupport<T> implements AbstractService<T> {
    public boolean save(T entry) {
        return super.save(entry);
    }

    public T get(Integer id) {
        return super.get(id);
    }

    public boolean update(T entry) {
        return super.update(entry);
    }

    public boolean updateNotNull(T entry) {
        return super.updateNotNull(entry);
    }

    public boolean delete(Integer id) {
        return super.delete(id);
    }

    public boolean deletes(List<Integer> ids) {
        return super.deletes(ids);
    }

    public List<T> getList(QueryMapping queryMapping) {
        return super.getList(queryMapping);
    }

    public Pager<T> getListPage(Pager<T> pager, QueryMapping queryMapping) {
        return super.getListPage(pager, queryMapping);
    }

    public int count(QueryMapping queryMapping) {
        return super.count(queryMapping);
    }

    protected List<Integer> getIdList(List<? extends AbstractModel> abstractModels) {
        List<Integer> integerList = new ArrayList<Integer>();
        if (abstractModels == null) {
            return integerList;
        }
        for (Iterator<AbstractModel> iterator = (Iterator<AbstractModel>) abstractModels.iterator(); iterator.hasNext(); ) {
            integerList.add(iterator.next().getId());
        }
        return integerList;
    }
}
