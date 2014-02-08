package org.tango.framework.service;

import org.tango.core.abs.AbstractService;
import org.tango.framework.domain.Privilege;

import java.util.List;

/**
 * User: tango
 * Date: 13-11-23
 * Time: 下午9:43
 */
public interface PrivilegeService extends AbstractService<Privilege> {
    boolean grantMenu(List<Integer> ids, List<Integer> mes, List<Integer> grants);

    List<Integer>  getGrantMenu(Integer uid, List<Integer> mes);
}
