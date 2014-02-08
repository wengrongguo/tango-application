package org.tango.framework.service;

import org.tango.core.abs.AbstractService;
import org.tango.framework.domain.Role;

import java.util.List;

/**
 * User: tango
 * Date: 13-11-23
 * Time: 下午6:14
 */
public interface RoleService extends AbstractService<Role> {

    List<Integer> getGrantPrivilege(Integer uid, List<Integer> privileges);

    boolean grantPrivilege(List<Integer> ids, List<Integer> privileges, List<Integer> grants);
}
