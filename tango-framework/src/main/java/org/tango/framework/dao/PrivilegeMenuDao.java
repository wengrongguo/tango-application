package org.tango.framework.dao;

import org.tango.core.abs.AbstractDao;
import org.tango.framework.domain.Menu;
import org.tango.framework.domain.Privilege;
import org.tango.framework.domain.PrivilegeMenuMap;

import java.util.List;

/**
 * User: tango
 * Date: 13-11-27
 * Time: 下午9:13
 */
public interface PrivilegeMenuDao extends AbstractDao<PrivilegeMenuMap> {

    boolean deleteGrantMenu(List<Integer> privileges, List<Integer> menus);

    void saveGrantMenu(List<Integer> grants, List<Integer> menus);

    List<Menu> getMenuList(List<Privilege> privileges);
}
