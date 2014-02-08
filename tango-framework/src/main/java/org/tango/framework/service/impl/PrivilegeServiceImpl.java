package org.tango.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tango.core.bean.QueryMapping;
import org.tango.framework.dao.PrivilegeMenuDao;
import org.tango.framework.dao.RolePrivilegeDao;
import org.tango.framework.domain.Privilege;
import org.tango.framework.domain.PrivilegeMenuMap;
import org.tango.framework.domain.RolePrivilegeMap;
import org.tango.framework.service.PrivilegeService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: tango
 * Date: 13-11-23
 * Time: 下午9:43
 */
@Service
public class PrivilegeServiceImpl extends FrameworkServiceSupport<Privilege> implements PrivilegeService {

    @Autowired
    private PrivilegeMenuDao privilegeMenuDao;

    @Override
    public boolean grantMenu(List<Integer> privileges, List<Integer> menus, List<Integer> grants) {
        privilegeMenuDao.deleteGrantMenu(privileges, menus);
        privilegeMenuDao.saveGrantMenu(privileges, grants);
        return true;
    }

    @Override
    public List<Integer> getGrantMenu(Integer uid, List<Integer> menus) {
        //TODO:去除上级权限的菜单列表(如果非删除权限)
        List<Integer> grantMenus = new ArrayList<Integer>();
        if (menus == null || menus.isEmpty()) {
            return grantMenus;
        }
        List<PrivilegeMenuMap> privilegeMenuMaps = privilegeMenuDao.getList(new QueryMapping().addEquals("privilege.id", uid).addIn("menu.id", menus.toArray(new Integer[0])));
        for (Iterator<PrivilegeMenuMap> iterator = privilegeMenuMaps.iterator(); iterator.hasNext(); ) {
            PrivilegeMenuMap privilegeMenuMap = iterator.next();
            grantMenus.add(privilegeMenuMap.getMenu().getId());
        }
        return grantMenus;
    }
}
