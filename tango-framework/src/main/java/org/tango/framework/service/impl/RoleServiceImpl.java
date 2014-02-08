package org.tango.framework.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tango.core.bean.QueryMapping;
import org.tango.framework.dao.RolePrivilegeDao;
import org.tango.framework.domain.Role;
import org.tango.framework.domain.RolePrivilegeMap;
import org.tango.framework.service.RoleService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 角色业务实现类
 * User: tango
 * Date: 13-11-23
 * Time: 下午6:16
 */
@Service
public class RoleServiceImpl extends FrameworkServiceSupport<Role> implements RoleService {

    @Autowired
    private RolePrivilegeDao rolePrivilegeDao;

    @Override
    public List<Integer> getGrantPrivilege(Integer uid, List<Integer> privileges) {
        List<Integer> grantPrivileges = new ArrayList<Integer>();
        if (privileges == null || privileges.isEmpty()) {
            return grantPrivileges;
        }
        List<RolePrivilegeMap> rolePrivilegeMaps = rolePrivilegeDao.getList(new QueryMapping().addEquals("role.id", uid).addIn("privilege.id", privileges.toArray(new Integer[0])));
        for (Iterator<RolePrivilegeMap> iterator = rolePrivilegeMaps.iterator(); iterator.hasNext(); ) {
            RolePrivilegeMap rolePrivilegeMap = iterator.next();
            grantPrivileges.add(rolePrivilegeMap.getPrivilege().getId());
        }
        return grantPrivileges;
    }

    @Override
    public boolean grantPrivilege(List<Integer> roles, List<Integer> privileges, List<Integer> grants) {
        rolePrivilegeDao.deleteGrantPrivilege(privileges, roles);
        rolePrivilegeDao.saveGrantPrivilege(grants, roles);
        return true;
    }
}
