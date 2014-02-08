package org.tango.framework;

import org.apache.struts2.ServletActionContext;
import org.tango.core.constant.Constant;
import org.tango.core.exception.LoginException;
import org.tango.framework.bean.LoginInfo;
import org.tango.framework.domain.Menu;
import org.tango.framework.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static org.tango.core.constant.Constant.KEY_USER;

/**
 * User: tango
 * Date: 13-6-27
 * Time: 下午5:28
 */
public class ApplicationContext {

    public static final LoginInfo getLoginInfo() {
        if (getRequest().getSession().getAttribute(KEY_USER) == null) {
            throw new LoginException();
        }
        return (LoginInfo) getRequest().getSession().getAttribute(KEY_USER);
    }

    public static final void setLoginInfo(LoginInfo loginInfo) {
        getSession().setAttribute(KEY_USER, loginInfo);
    }

    public static final User getLoginUser() {
        return getLoginInfo().getUser();
    }

    public static List<Menu> getMenus() {
        return getLoginInfo().getMenus();
    }

    public static final HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    public static final HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    public static final HttpSession getSession() {
        return getRequest().getSession();
    }


}
