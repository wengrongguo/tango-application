package org.tango.framework.dao;

import org.tango.core.abs.AbstractDao;
import org.tango.framework.domain.Privilege;
import org.tango.framework.domain.Role;
import org.tango.framework.domain.RolePrivilegeMap;

import java.util.List;

/**
 * User: tango
 * Date: 13-11-27
 * Time: 下午9:13
 */
public interface RolePrivilegeDao extends AbstractDao<RolePrivilegeMap> {

    boolean deleteGrantPrivilege(List<Integer> privileges, List<Integer> roles);

    void saveGrantPrivilege(List<Integer> grants, List<Integer> roles);

    List<Privilege> getPrivilegeList(List<Integer> roles);
}
