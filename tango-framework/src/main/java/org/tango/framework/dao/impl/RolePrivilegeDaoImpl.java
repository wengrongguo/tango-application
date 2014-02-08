package org.tango.framework.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.tango.core.constant.Constant;
import org.tango.framework.dao.RolePrivilegeDao;
import org.tango.framework.domain.*;

import java.util.List;

/**
 * User: tango
 * Date: 13-12-1
 * Time: 下午2:21
 */
@Repository
public class RolePrivilegeDaoImpl extends FrameworkDaoSupport<RolePrivilegeMap> implements RolePrivilegeDao {

    @Override
    public boolean deleteGrantPrivilege(List<Integer> privileges, List<Integer> roles) {
        Query query = super.getSession().createQuery("delete RolePrivilegeMap rpm where rpm.privilege.id in (:privileges) and rpm.role.id in (:roles)");
        query.setParameterList("privileges", privileges);
        query.setParameterList("roles", roles);
        return query.executeUpdate() > 0;
    }

    @Override
    public void saveGrantPrivilege(List<Integer> grants, List<Integer> roles) {
        Session session = super.getSession();
        for (int i = 0; i < grants.size(); i++) {
            for (int j = 0; j < roles.size(); j++) {
                RolePrivilegeMap rolePrivilegeMap = new RolePrivilegeMap();
                //
                Privilege privilege = new Privilege();
                privilege.setId(grants.get(i));
                rolePrivilegeMap.setPrivilege(privilege);
                //
                Role role = new Role();
                role.setId(roles.get(j));
                rolePrivilegeMap.setRole(role);
                //
                session.save(rolePrivilegeMap);
            }
        }
    }

    @Override
    public List<Privilege> getPrivilegeList(List<Integer> roles) {
        if (roles.isEmpty())
            return null;
        Session session = super.getSession();
        Criteria criteria = session.createCriteria(Privilege.class);
        criteria.add(Restrictions.eq("stated", Constant.STATE_EFFECTIVE));
        criteria.createCriteria("roles").createCriteria("role").add(Restrictions.in("id", roles));
        return criteria.list();
    }
}
