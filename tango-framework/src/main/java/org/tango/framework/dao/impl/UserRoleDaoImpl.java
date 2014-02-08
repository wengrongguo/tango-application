package org.tango.framework.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.tango.core.bean.QueryMapping;
import org.tango.core.constant.Constant;
import org.tango.framework.dao.UserRoleDao;
import org.tango.framework.domain.Role;
import org.tango.framework.domain.User;
import org.tango.framework.domain.UserRoleMap;

import java.util.List;

/**
 * User: tango
 * Date: 13-11-27
 * Time: 下午9:15
 */
@Repository
public class UserRoleDaoImpl extends FrameworkDaoSupport<UserRoleMap> implements UserRoleDao {

    @Override
    public boolean deleteGrantRole(List<Integer> roles, List<Integer> users) {
        Query query = super.getSession().createQuery("delete UserRoleMap urm where urm.role.id in (:roles) and urm.user.id in (:users)");
        query.setParameterList("roles", roles);
        query.setParameterList("users", users);
        return query.executeUpdate() > 0;
    }

    @Override
    public void saveGrantRole(List<Integer> grants, List<Integer> users) {
        Session session = super.getSession();
        for (int i = 0; i < grants.size(); i++) {
            for (int j = 0; j < users.size(); j++) {
                UserRoleMap userRoleMap = new UserRoleMap();
                //
                User user = new User();
                user.setId(users.get(j));
                userRoleMap.setUser(user);
                //
                Role role = new Role();
                role.setId(grants.get(i));
                userRoleMap.setRole(role);
                //
                session.save(userRoleMap);
            }
        }
    }

    @Override
    public List<Role> getRoleList(Integer userId) {
        Session session = super.getSession();
        Criteria criteria = session.createCriteria(Role.class);
        criteria.add(Restrictions.eq("stated", Constant.STATE_EFFECTIVE));
        criteria.createCriteria("users").createCriteria("user").add(Restrictions.eq("id", userId));
        return criteria.list();
    }
}
