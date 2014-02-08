package org.tango.framework.web;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import org.tango.core.annotation.Access;
import org.tango.core.constant.Constant;
import org.tango.core.exception.LoginException;
import org.tango.core.exception.PrivilegeException;
import org.tango.framework.ApplicationContext;
import org.tango.framework.domain.Menu;
import org.tango.struts.interceptor.AbstractPrivilegeInterceptor;
import org.tango.utils.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Iterator;
import java.util.List;

/**
 * User: tango
 * Date: 13-12-7
 * Time: 下午10:36
 */
public class ApplicationInterceptor extends AbstractPrivilegeInterceptor {

    @Override
    public void interceptLogin(HttpServletRequest request, ActionInvocation invocation, ActionSupport actionSupport) {
        Access access = getAccessInLRU(invocation);
        if (access == null || access.login()) {
            //
            String refer = request.getRequestURI();
            if (!refer.endsWith(login)/* && refer.matches(".*admin.*")*/) {
                if (ApplicationContext.getLoginUser() == null) {
                    throw new LoginException();
                }
            }
        }
    }

    @Override
    public void interceptPrivilege(HttpServletRequest request, ActionInvocation invocation, ActionSupport actionSupport) throws ServletException, IOException {
        Access access = getAccessInLRU(invocation);
        if (access == null || access.privilege()) {
            ActionProxy actionProxy = invocation.getProxy();
            String namespace = actionProxy.getNamespace();
            String action = actionProxy.getActionName();

            log.info(StringUtils.format("权限拦截 - {0}/{1}", new Object[]{namespace, action}));
            //
            List<Menu> menus = (List<Menu>) ApplicationContext.getMenus();
            for (Iterator<Menu> iterator = menus.iterator(); iterator.hasNext(); ) {
                Menu menu = iterator.next();
                String menuUrl = menu.getLink();
                if (menuUrl == null) {
                    continue;
                }
                if (namespace.concat("/").concat(action).equals(menuUrl.replaceAll("\\.action.*", Constant.EMPTY))) {
                    return;
                }
            }
            throw new PrivilegeException();
        }
    }
}
