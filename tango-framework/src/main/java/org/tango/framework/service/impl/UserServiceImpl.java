package org.tango.framework.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tango.core.bean.QueryMapping;
import org.tango.core.constant.Constant;
import org.tango.core.exception.LoginException;
import org.tango.framework.bean.LoginInfo;
import org.tango.framework.dao.LoginLogDao;
import org.tango.framework.dao.PrivilegeMenuDao;
import org.tango.framework.dao.RolePrivilegeDao;
import org.tango.framework.dao.UserRoleDao;
import org.tango.framework.domain.*;
import org.tango.framework.service.UserService;
import org.tango.utils.SecurityUtils;
import org.tango.utils.StringUtils;
import org.tango.utils.enums.EncryptedType;

import java.util.*;

/**
 * User: tango
 * Date: 13-11-22
 * Time: 下午10:21
 */
@Service
public class UserServiceImpl extends FrameworkServiceSupport<User> implements UserService {

    private static final Log log = LogFactory.getLog(UserServiceImpl.class);

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RolePrivilegeDao rolePrivilegeDao;

    @Autowired
    private PrivilegeMenuDao privilegeMenuDao;

    @Autowired
    private LoginLogDao loginLogDao;

    @Override
    public boolean save(User user) {
        user.setPassword("123456");
        user.setPassword(SecurityUtils.Encryption(user.getPassword(), EncryptedType.MD5));
        return super.save(user);
    }

    @Override
    public boolean repass(User user) {
        user.setPassword(SecurityUtils.Encryption(user.getPassword(), EncryptedType.MD5));
        return super.updateNotNull(user);
    }

    @Override
    public boolean grantRole(List<Integer> users, List<Integer> roles, List<Integer> grants) {
        userRoleDao.deleteGrantRole(roles, users);
        userRoleDao.saveGrantRole(grants, users);
        return true;
    }

    @Override
    public List<Integer> getGrantRole(Integer uid, List<Integer> roles) {
        List<Integer> grantRoles = new ArrayList<Integer>();
        if (roles == null) {
            return grantRoles;
        }
        //
        List<UserRoleMap> userRoleMaps = userRoleDao.getList(new QueryMapping().addEquals("user.id", uid).addIn("role.id", roles.toArray(new Integer[0])));
        for (Iterator<UserRoleMap> iterator = userRoleMaps.iterator(); iterator.hasNext(); ) {
            UserRoleMap userRoleMap = iterator.next();
            grantRoles.add(userRoleMap.getRole().getId());
        }
        return grantRoles;
    }

    @Override
    public LoginInfo login(String account, String password, String ip) {
        Date loginDate = new Date();
        log.info(StringUtils.format("用户登陆 : {0} / {1}", account, loginDate));
        //
        User user = null;
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setIp(ip);
        //
        List<User> users = this.getList(new QueryMapping().addEquals("account", account));
        //校验用户名存在性
        if (users.isEmpty()) {
            loginInfo.setError(LoginException.Error.UN_EXISTS);
        } else {
            user = users.iterator().next();
        }
        //
        if (!loginInfo.isHasError()) {
            if (!account.equals(user.getAccount()) ||//校验用户名密码正确性
                    !password.equals(user.getPassword())) {
                loginInfo.setError(LoginException.Error.ACCOUNT_ERROR);
            } else if (user.getStated().intValue() != //校验用户状态
                    Constant.STATE_EFFECTIVE.intValue()) {
                loginInfo.setError(LoginException.Error.STATE_ERROR);
            }
        }
        if (!loginInfo.isHasError()) {
            //查询用户角色
            List<Role> roles = userRoleDao.getRoleList(user.getId());
            //查询用户权限
            List<Privilege> privileges = rolePrivilegeDao.getPrivilegeList(getIdList(roles));
            //查询用户菜单
            List<Menu> menus = privilegeMenuDao.getMenuList(privileges);
            loginInfo.setUser(user);
            loginInfo.setRoles(roles);
            loginInfo.setMenus(menus);
            loginInfo.setPrivileges(privileges);
            user.setLastTime(new Date());
            Integer loginCount = user.getLoginCount();
            user.setLoginCount(loginCount == null ? 1 : loginCount + 1);
            super.update(user);
        }
        loginLogDao.save(parseLoginLog(loginInfo, account, LoginLog.LoginType.LOGIN));
        return loginInfo;
    }

    @Override
    public void logout(User user, String ip) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUser(user);
        loginInfo.setIp(ip);
        loginLogDao.save(parseLoginLog(loginInfo, user.getAccount(), LoginLog.LoginType.LOGOUT));
    }

    private LoginLog parseLoginLog(LoginInfo loginInfo, String account, LoginLog.LoginType loginType) {
        LoginLog loginLog = new LoginLog();
        User user = loginInfo.getUser();
        if (user != null) {
            loginLog.setUserId(user.getId().toString());
            loginLog.setRemark(StringUtils.format("{0}:{1}:{2}", loginInfo.getIp(), user.getAccount(), loginLog.getStated()));
            loginLog.setAccount(loginInfo.getUser().getAccount());
        }
        loginLog.setDate(new Date());
        loginLog.setType(loginType.getValue());
        loginLog.setStated(loginInfo.isHasError() ? "-1" : "1");
        loginLog.setIp(loginInfo.getIp());
        return loginLog;
    }
}
