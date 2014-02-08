package org.tango.framework.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.tango.core.bean.QueryMapping;
import org.tango.framework.domain.Role;
import org.tango.framework.service.RoleService;
import org.tango.struts.action.AbstractCRUDAction;
import org.tango.struts.bean.ActionResult;

import java.util.List;

import static org.tango.core.constant.StrutsConstant.*;

/**
 * User: tango
 * Date: 13-6-13
 * Time: 下午2:13
 */
@Controller
@Scope(PROTOTYPE)
@Namespace("/system/role")
@ParentPackage(ADMIN_DEFAULT)
public class RoleAction extends AbstractCRUDAction<Role> {

    @Autowired
    private RoleService roleService;

    private Role role;

    private List<Integer> pris;

    private List<Integer> grants;

    @Override
    public void prepare() {
        this.bindRequireProperty(roleService, role, getQueryMapping());
    }

    @Action(value = INDEX, results = {@Result(name = SUCCESS, location = "../role.jsp")})
    public String index() throws Exception {
        return SUCCESS;
    }

    @Action(value = "privilege", results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String privilege() throws Exception {
        actionResult = ActionResult.getActionResult(roleService.getGrantPrivilege(this.getUid(), pris));
        return SUCCESS;
    }

    @Action(value = "grant", results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String grant() throws Exception {
        actionResult = ActionResult.getActionResult(roleService.grantPrivilege(this.getIds(), pris, this.grants));
        return SUCCESS;
    }


    private QueryMapping getQueryMapping() {
        /*
        QueryMapping queryMapping = new QueryMapping();
        if (!StringUtils.isBlank(user.getName())) {
            queryMapping.addLike("name", "%" + user.getName() + "%");
        }
        if (!StringUtils.isBlank(user.getAccount())) {
            queryMapping.addEquals("account", user.getAccount());
        }
        if (!StringUtils.isBlank(user.getPhone())) {
            queryMapping.addEquals("phone", user.getPhone());
        }
        if (user.getGender() != null) {
            queryMapping.addEquals("gender", user.getGender());
        }
        if (user.getStated() != null) {
            queryMapping.addEquals("stated", user.getStated());
        }
        return queryMapping;
        */

        return null;
    }

    @Override
    public Role getModel() {
        return role == null ? (role = new Role()) : role;
    }

    public List<Integer> getGrants() {
        return grants;
    }

    public void setGrants(List<Integer> grants) {
        this.grants = grants;
    }

    public List<Integer> getPris() {
        return pris;
    }

    public void setPris(List<Integer> pris) {
        this.pris = pris;
    }
}
