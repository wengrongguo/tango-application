package org.tango.framework.dao.impl;

import org.hibernate.SessionFactory;
import org.tango.core.abs.AbstractModel;
import org.tango.hibernate.support.dao.HibernateDaoSupport;

import javax.annotation.Resource;

/**
 * User: tango
 * Date: 13-11-17
 * Time: 下午9:33
 */
public class FrameworkDaoSupport<T extends AbstractModel> extends HibernateDaoSupport<T> {

    @Override
    @Resource(name = "frameworkSessionFactory")
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
