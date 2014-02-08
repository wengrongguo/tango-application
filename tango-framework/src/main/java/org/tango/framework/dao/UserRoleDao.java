package org.tango.framework.dao;

import org.tango.core.abs.AbstractDao;
import org.tango.framework.domain.Role;
import org.tango.framework.domain.UserRoleMap;

import java.util.List;

/**
 * User: tango
 * Date: 13-11-27
 * Time: 下午9:13
 */
public interface UserRoleDao extends AbstractDao<UserRoleMap> {

    boolean deleteGrantRole(List<Integer> roles, List<Integer> users);

    void saveGrantRole(List<Integer> grants, List<Integer> users);

    List<Role> getRoleList(Integer userId);
}
