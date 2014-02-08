/*
* Copyright (c) 2012. tAngo
* 	Email : org.java.tango@gmail.com
*/

package org.tango.hibernate.support.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.tango.core.abs.AbstractDao;
import org.tango.core.abs.AbstractModel;
import org.tango.core.bean.Callable;
import org.tango.core.bean.Pager;
import org.tango.core.bean.QueryMapping;
import org.tango.utils.Converters;
import org.tango.utils.DateUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Timestamp;
import java.util.*;


/**
 * @param <T>2012-5-7
 * @author tAngo
 */
public class HibernateDaoSupport<T extends AbstractModel> implements AbstractDao<T> {

    private SessionFactory sessionFactory;

    protected Class<?> clazz = null;

    public HibernateDaoSupport() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

    @Override
    public boolean save(T entity) {
        try {
            this.getSession().save(entity);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public void save(T... ens) {
        Session session = this.getSession();
        for (int i = 0; i < ens.length; i++) {
            session.save(ens[i]);
        }
    }

    @Override
    public boolean delete(Integer serializableId) {
        try {
            Session session = this.getSession();
            T t = (T) session.get(clazz, serializableId);
            if (t != null) {
                session.delete(t);
            }
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean deletes(List<Integer> ids) {
        try {
            for (Iterator<Integer> iterator = ids.iterator(); iterator.hasNext(); ) {
                this.delete(iterator.next());
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean deletes(QueryMapping queryMapping) {
        Criteria criteria = this.createCriteria();
        this.addParameters(criteria, queryMapping);
        List<T> list = criteria.list();
        List<Integer> ids = new ArrayList<Integer>();
        for (Iterator<T> iterator = list.iterator(); iterator.hasNext(); ) {
            ids.add(iterator.next().getId());
        }
        return this.deletes(ids);
    }


    @Override
    public boolean delete(T entity) {
        try {
            this.getSession().delete(entity);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(T entity) {
        try {
            this.getSession().update(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateNotNull(T entity) {
        try {
            T t = this.get(entity.getId());

            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] descriptor = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < descriptor.length; i++) {
                PropertyDescriptor propertyDescriptor = descriptor[i];
                if ("class".equals(propertyDescriptor.getName())) {
                    continue;
                }
                Method method = propertyDescriptor.getReadMethod();
                Object propValue = method.invoke(entity);
                if (propValue != null) {
                    propertyDescriptor.getWriteMethod().invoke(t, propValue);
                }
            }
            this.update(t);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public T get(Integer serializableId) {
        return (T) this.getSession().get(clazz, serializableId);
    }

    @Override
    public List<T> get(int[] serializableIds) {
        Criteria criteria = createCriteria();
        criteria.add(Restrictions.in("id", Arrays.asList(serializableIds).toArray(new Integer[0])));
        return criteria.list();
    }

    @Override
    public List<T> getList(QueryMapping queryMapping) {
        return this.addParameters(createCriteria(), queryMapping).list();
    }

    @Override
    public Pager<T> getListPage(Pager<T> pager, QueryMapping queryMapping) {
        int rows = count(queryMapping);
        pager.setRows(rows);
        //
        Criteria criteria = createCriteria();
        this.addParameters(criteria, queryMapping);
        //pageSize
        if (pager.isDoPage()) {
            criteria.setFirstResult(pager.getStart());
            criteria.setMaxResults(pager.getPageSize());
        }
        pager.setDataList(criteria.list());
        return pager;
    }

    @Override
    public int count(QueryMapping queryMapping) {
        Criteria criteria = createCriteria();
        criteria.setProjection(Projections.rowCount());
        this.addParameters(criteria, queryMapping);
        return Converters.string2Int(criteria.uniqueResult().toString(), 0);
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    protected Criteria createCriteria() {
        Session session = this.getSession();
        return session.createCriteria(this.clazz);
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected Criteria addParameters(Criteria criteria, QueryMapping queryMappings) {
        if (queryMappings != null) {
            List<Criterion> criterionList = new ArrayList<Criterion>();
            //order
            addOrderParam(criteria, queryMappings.getOrders());
            addParameterCriterion(queryMappings, criterionList);
            for (Iterator<Criterion> iterator = criterionList.iterator(); iterator.hasNext(); ) {
                criteria.add(iterator.next());
            }
        }
        return criteria;
    }

    private void addParameterCriterion(QueryMapping queryMappings, List<Criterion> criterionList) {
        //between
        addBetweenParam(criterionList, queryMappings.getBetween());
        //lt
        addLtParam(criterionList, queryMappings.getLts());
        //gt
        addGtParam(criterionList, queryMappings.getGts());
        //equals
        addEqParam(criterionList, queryMappings.getEquals());
        //likes
        addLikeParam(criterionList, queryMappings.getLikes());
        //ins
        addInParam(criterionList, queryMappings.getIns());
    }

    private void addParameter(List<Criterion> criterionList, List<?> pvs, Callable callable) {
        if (pvs == null) return;
        for (Iterator<?> iterator = pvs.iterator(); iterator.hasNext(); ) {
            callable.call(criterionList, iterator.next());
        }
    }

    private void addInParam(List<Criterion> criterionList, List<Map.Entry<String, Object[]>> ins) {
        addParameter(criterionList, ins, new Callable() {
            @Override
            public void call(Object... params) {
                Map.Entry<String, Object[]> entry = (Map.Entry<String, Object[]>) params[1];
                ((List<Criterion>) params[0]).add(Restrictions.in(entry.getKey(), entry.getValue()));
            }
        });
    }

    private void addOrderParam(Criteria criteria, List<Map.Entry<String, Boolean>> orders) {
        if (orders == null) return;
        for (Iterator<Map.Entry<String, Boolean>> iterator = orders.iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Boolean> next = (Map.Entry<String, Boolean>) iterator.next();
            criteria.addOrder(next.getValue() ? Order.asc(next.getKey()) : Order.desc(next.getKey()));
        }
    }

    private void addLtParam(List<Criterion> criterionList, List<Map.Entry<String, Object>> lts) {
        addParameter(criterionList, lts, new Callable() {
            @Override
            public void call(Object... params) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) params[1];
                ((List<Criterion>) params[0]).add(Restrictions.le(entry.getKey(), entry.getValue()));
            }
        });
    }

    private void addGtParam(List<Criterion> criterionList, List<Map.Entry<String, Object>> gts) {
        addParameter(criterionList, gts, new Callable() {
            @Override
            public void call(Object... params) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) params[1];
                ((List<Criterion>) params[0]).add(Restrictions.ge(entry.getKey(), entry.getValue()));
            }
        });
    }

    private void addEqParam(List<Criterion> criterionList, List<Map.Entry<String, Object>> equals) {
        addParameter(criterionList, equals, new Callable() {
            @Override
            public void call(Object... params) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) params[1];
                ((List<Criterion>) params[0]).add(Restrictions.eq(entry.getKey(), entry.getValue()));
            }
        });
    }

    private void addLikeParam(List<Criterion> criterionList, List<Map.Entry<String, Object>> likes) {
        addParameter(criterionList, likes, new Callable() {
            @Override
            public void call(Object... params) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) params[1];
                ((List<Criterion>) params[0]).add(Restrictions.like(entry.getKey(), entry.getValue()));
            }
        });
    }

    private void addBetweenParam(List<Criterion> criterionList, List<Map.Entry<String, Object[]>> between) {
        if (between == null) return;
        for (Iterator<Map.Entry<String, Object[]>> iterator = between.iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Object[]> entry = iterator.next();
            String prop = entry.getKey();
            Object[] bet = entry.getValue();
            //
            Object begin = bet[0];
            Object end = null;
            if (bet.length > 1) {
                end = bet[1];
            }
            //
            if (begin instanceof Date || begin instanceof Timestamp) {
                if (begin != null && end != null) {
                    criterionList.add(Restrictions.between(prop, begin, end));
                } else if (begin == null && end != null) {
                    criterionList.add(Restrictions.between(prop, DateUtils.parseDateByString("1900-01-01"), end));
                } else if (begin != null && end == null) {
                    criterionList.add(Restrictions.between(prop, begin, new Date()));
                }
            } else if (begin != null && end != null) {
                criterionList.add(Restrictions.between(prop, begin, end));
            }
        }
    }
}
