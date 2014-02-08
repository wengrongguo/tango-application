package org.tango.hibernate.abs;

import org.hibernate.proxy.LazyInitializer;
import org.tango.core.abs.AbstractModel;

import javax.persistence.MappedSuperclass;

/**
 * User: tango
 * Date: 13-6-13
 * Time: 下午2:16
 */
@MappedSuperclass
public abstract class AbstractHibernateModel extends AbstractModel {
}
