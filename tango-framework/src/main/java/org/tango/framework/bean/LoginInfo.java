package org.tango.framework.bean;

import org.tango.core.exception.LoginException;
import org.tango.framework.domain.Menu;
import org.tango.framework.domain.Privilege;
import org.tango.framework.domain.Role;
import org.tango.framework.domain.User;

import java.util.List;

/**
 * User: tango
 * Date: 13-12-7
 * Time: 下午6:11
 */
public class LoginInfo {
    private User user;

    private List<Role> roles;

    private List<Privilege> privileges;

    private List<Menu> menus;

    private boolean hasError;

    private LoginException.Error error;

    private String ip;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public boolean isHasError() {
        return hasError || error != null;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public LoginException.Error getError() {
        return error;
    }

    public void setError(LoginException.Error error) {
        this.error = error;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
