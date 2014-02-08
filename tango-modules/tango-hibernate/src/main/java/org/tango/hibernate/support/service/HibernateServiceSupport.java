/*
 * Copyright (c) 2012. tAngo
 * 	Email : org.java.tango@gmail.com
 */

package org.tango.hibernate.support.service;

import org.tango.core.abs.AbstractModel;
import org.tango.core.abs.AbstractService;
import org.tango.hibernate.support.dao.HibernateDaoSupport;

/**
 * User: tAngo
 * Date: 12-11-11
 * Time: 下午4:46
 */
public class HibernateServiceSupport<T extends AbstractModel> extends HibernateDaoSupport<T> implements AbstractService<T> {
}
