package org.tango.framework.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.tango.core.annotation.Access;
import org.tango.core.bean.DataDefined;
import org.tango.core.constant.Constant;
import org.tango.core.constant.NumberConstant;
import org.tango.core.exception.LoginException;
import org.tango.framework.ApplicationContext;
import org.tango.framework.bean.LoginInfo;
import org.tango.framework.domain.Menu;
import org.tango.framework.domain.User;
import org.tango.framework.service.UserService;
import org.tango.struts.action.AbstractAction;
import org.tango.struts.bean.ActionResult;
import org.tango.utils.SecurityUtils;
import org.tango.utils.enums.EncryptedType;
import org.tango.utils.servlet.RequestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

import static org.tango.core.constant.StrutsConstant.*;

/**
 * User: tAngo
 * Date: 12-12-21
 * Time: 上午2:47
 */
@Controller
@Scope(PROTOTYPE)
@Namespace("/system")
@ParentPackage(ADMIN_DEFAULT)
public class LoginAction extends AbstractAction {

    private String account;

    private String password;

    @Autowired
    private UserService userService;

    /**
     * 登陆
     *
     * @return 系统主页面
     */
    @Action(value = "login", results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_JSON})})
    public String login() throws Exception {
        HttpServletRequest request = ApplicationContext.getRequest();
        password = SecurityUtils.Encryption(password, EncryptedType.MD5);
        //
        if (checkLoginInSession()) {
            actionResult = ActionResult.getActionResult(SUCCESS);
            return SUCCESS;
        }
        //
        LoginInfo loginInfo = userService.login(account, password, RequestUtils.getRemoteAddress(request));
        if (loginInfo.isHasError()) {
            throw new LoginException(loginInfo.getError());
        }
        //
        if (loginInfo.getUser() != null) {
            ApplicationContext.setLoginInfo(loginInfo);
            actionResult = ActionResult.getActionResult(SUCCESS);
        }
        return SUCCESS;
    }

    /*
     * 跳转后台管理首页
     */
    @Action(value = INDEX, results = {@Result(name = SUCCESS, location = "index.jsp")})
    public String index() throws Exception {
        return SUCCESS;
    }

    @Access(login = true,privilege = false)
    @Action(value = "top", results = {@Result(name = SUCCESS, location = "common/top.jsp")})
    public String top() throws Exception {
        return SUCCESS;
    }


    /**
     * 退出系统
     *
     * @return 登陆页面
     */
    @Access(login = false)
    @Action(value = "logout", results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_JSON})})
    public String logout() {
        HttpServletRequest request = ApplicationContext.getRequest();
        if (ApplicationContext.getLoginUser() != null) {
            User loginUser = ApplicationContext.getLoginUser();
            if (loginUser != null) {
                userService.logout(loginUser, RequestUtils.getRemoteAddress(request));
            }
            request.getSession().invalidate();
            actionResult = ActionResult.getActionResult(SUCCESS);
        }
        return SUCCESS;
    }

    /**
     * 系统菜单
     *
     * @return 系统菜单Json
     */
    @Action(value = "menu", results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_JSON})})
    public String menu() {
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
        DataDefined<Menu> dataDefined = new DataDefined<Menu>() {
            @Override
            public Map<String, Object> defined(Menu menu) {
                Map<String, Object> defined = new HashMap<String, Object>();
                defined.put(Constant.ID, menu.getId());
                defined.put(Constant.TEXT, menu.getName());
                defined.put(Constant.ATTRIBUTES, menu);
                return defined;
            }
        };
        List<Menu> menus = (List<Menu>) ApplicationContext.getMenus();
        //
        for (Iterator<Menu> iterator = menus.iterator(); iterator.hasNext(); ) {
            Menu menu = iterator.next();
            if (NumberConstant.ZERO == menu.getParentMenu().intValue() && NumberConstant.ONE == menu.getSystem().intValue()) {
                Map<String, Object> params = dataDefined.defined(menu);
                params.put(CHILDREN, getMenuByParentId(dataDefined, menus, menu.getId()));
                treeList.add(params);
            }
        }
        actionResult = ActionResult.getActionResult(treeList);
        return SUCCESS;
    }

    private List<Map<String, Object>> getMenuByParentId(DataDefined<Menu> dataDefined, List<Menu> menus, Integer parentId) {
        List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
        for (Iterator<Menu> iterator = menus.iterator(); iterator.hasNext(); ) {
            Menu menu = iterator.next();
            if (menu.getParentMenu().intValue() == parentId.intValue() && NumberConstant.ONE == menu.getSystem().intValue()) {
                children.add(dataDefined.defined(menu));
            }
        }
        return children;
    }

    /*
     * 检查登陆用户,对比Session
     */
    private boolean checkLoginInSession() {
        HttpSession session = ApplicationContext.getSession();
        Object loginInfoSwap = session.getAttribute(KEY_USER);
        if (loginInfoSwap != null && loginInfoSwap instanceof LoginInfo) {
            LoginInfo loginInfo = (LoginInfo) loginInfoSwap;
            if (loginInfo.getUser() != null) {
                User user = (User) loginInfo.getUser();
                return STATE_EFFECTIVE.intValue() == user.getStated().intValue()//
                        && account.equals(user.getAccount())//
                        && password.equals(user.getPassword());
            }
        }
        return false;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
