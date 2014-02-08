package org.tango.framework.service;

import org.tango.core.abs.AbstractService;
import org.tango.framework.bean.LoginInfo;
import org.tango.framework.domain.User;

import java.util.List;
import java.util.Map;

/**
 * User: tango
 * Date: 13-11-22
 * Time: 下午10:21
 */
public interface UserService extends AbstractService<User> {
    boolean repass(User user);

    boolean grantRole(List<Integer> ids, List<Integer> roles, List<Integer> grants);

    List<Integer> getGrantRole(Integer uid, List<Integer> roles);

    LoginInfo login(String account, String password, String ip);

    void logout(User user, String ip);
}
