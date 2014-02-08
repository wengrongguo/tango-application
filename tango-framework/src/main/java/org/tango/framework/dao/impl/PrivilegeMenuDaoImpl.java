package org.tango.framework.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.tango.core.constant.Constant;
import org.tango.framework.dao.PrivilegeMenuDao;
import org.tango.framework.domain.Menu;
import org.tango.framework.domain.Privilege;
import org.tango.framework.domain.PrivilegeMenuMap;
import org.tango.framework.domain.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: tango
 * Date: 13-12-1
 * Time: 下午2:21
 */
@Repository
public class PrivilegeMenuDaoImpl extends FrameworkDaoSupport<PrivilegeMenuMap> implements PrivilegeMenuDao {

    @Override
    public boolean deleteGrantMenu(List<Integer> privileges, List<Integer> menus) {
        Query query = super.getSession().createQuery("delete PrivilegeMenuMap pmm where pmm.privilege.id in (:privileges) and pmm.menu.id in (:menus)");
        query.setParameterList("privileges", privileges);
        query.setParameterList("menus", menus);
        return query.executeUpdate() > 0;
    }

    @Override
    public void saveGrantMenu(List<Integer> grants, List<Integer> menus) {
        Session session = super.getSession();
        for (int i = 0; i < grants.size(); i++) {
            for (int j = 0; j < menus.size(); j++) {
                PrivilegeMenuMap privilegeMenuMap = new PrivilegeMenuMap();
                //
                Privilege privilege = new Privilege();
                privilege.setId(grants.get(i));
                privilegeMenuMap.setPrivilege(privilege);
                //
                Menu menu = new Menu();
                menu.setId(menus.get(j));
                privilegeMenuMap.setMenu(menu);
                //
                session.save(privilegeMenuMap);
            }
        }
    }

    @Override
    public List<Menu> getMenuList(List<Privilege> privileges) {
        if (privileges.isEmpty() || privileges == null)
            return null;
        Session session = super.getSession();
        List<Integer> privilegeIds = getPrivilegeIdAndParent(privileges, session);
        Criteria criteria = session.createCriteria(Menu.class).add(Restrictions.eq("stated", Constant.STATE_EFFECTIVE));
        criteria.createCriteria("privileges").createCriteria("privilege").add(Restrictions.in("id", privilegeIds));
        return criteria.list();
    }

    private List<Integer> getPrivilegeIdAndParent(List<Privilege> privileges, Session session) {
        List<Integer> privilegeIds = new ArrayList<Integer>();
        for (Iterator<Privilege> iterator = privileges.iterator(); iterator.hasNext(); ) {
            Privilege privilege = iterator.next();
            privilegeIds.add(privilege.getId());
            gerParentPrivilegeId(privilegeIds, privilege, session);
        }
        return privilegeIds;
    }

    private void gerParentPrivilegeId(List<Integer> privilegeIds, Privilege privilege, Session session) {
        if (privilege != null) {
            if (privilege.getParent().intValue() != 0) {
                privilegeIds.add(privilege.getParent());
            }
            Criteria criteria = session.createCriteria(Privilege.class);
            criteria.add(Restrictions.eq("id", privilege.getParent()));
            gerParentPrivilegeId(privilegeIds, (Privilege) criteria.uniqueResult(), session);
        }
    }
}
