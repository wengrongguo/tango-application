package org.tango.framework.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.tango.core.bean.QueryMapping;
import org.tango.framework.action.vo.PrivilegeVo;
import org.tango.framework.domain.Privilege;
import org.tango.framework.service.PrivilegeService;
import org.tango.struts.action.AbstractCRUDAction;
import org.tango.struts.bean.ActionResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.tango.core.constant.StrutsConstant.*;

/**
 * User: tango
 * Date: 13-6-13
 * Time: 下午2:13
 */
@Controller
@Scope(PROTOTYPE)
@Namespace("/system/privilege")
@ParentPackage(ADMIN_DEFAULT)
public class PrivilegeAction extends AbstractCRUDAction<Privilege> {

    @Autowired
    private PrivilegeService privilegeService;

    private Privilege privilege;

    private List<Integer> mes;

    private List<Integer> grants;

    @Override
    public void prepare() {
        this.bindRequireProperty(privilegeService, privilege, getQueryMapping());
    }

    @Action(value = TREE, results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String tree() throws Exception {
        List<PrivilegeVo> voList = PrivilegeVo.parse(privilegeService.getList(getQueryMapping()));
        Map<String, Object> response = new HashMap<String, Object>();
        response.put(ROWS, voList);
        actionResult = ActionResult.getActionResult(response);
        return SUCCESS;
    }

    @Action(value = "combotree", results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String combotree() throws Exception {
        List<Map<String, Object>> privileges = PrivilegeVo.parseList(privilegeService.getList(getQueryMapping()));
        actionResult = ActionResult.getActionResult(privileges);
        return SUCCESS;
    }

    @Action(value = "menu", results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String menu() throws Exception {
        actionResult = ActionResult.getActionResult(privilegeService.getGrantMenu(this.getUid(), mes));
        return SUCCESS;
    }

    @Action(value = "grant", results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String grant() throws Exception {
        actionResult = ActionResult.getActionResult(privilegeService.grantMenu(this.getIds(), mes, this.grants));
        return SUCCESS;
    }

    @Action(value = INDEX, results = {@Result(name = SUCCESS, location = "../privilege.jsp")})
    public String index() throws Exception {
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
    public Privilege getModel() {
        return privilege == null ? (privilege = new Privilege()) : privilege;
    }

    public List<Integer> getMes() {
        return mes;
    }

    public void setMes(List<Integer> mes) {
        this.mes = mes;
    }

    public List<Integer> getGrants() {
        return grants;
    }

    public void setGrants(List<Integer> grants) {
        this.grants = grants;
    }
}
