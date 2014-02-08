package org.tango.framework.action;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.asm.ClassAdapter;
import org.springframework.asm.ClassVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.tango.core.bean.QueryMapping;
import org.tango.framework.domain.User;
import org.tango.framework.service.UserService;
import org.tango.struts.action.AbstractCRUDAction;
import org.tango.struts.bean.ActionResult;

import java.util.Date;
import java.util.List;

import static org.tango.core.constant.StrutsConstant.*;

/**
 * User: tango
 * Date: 13-6-13
 * Time: 下午2:13
 */
@Controller
@Scope(PROTOTYPE)
@Namespace("/system/user")
@ParentPackage(ADMIN_DEFAULT)
public class UserAction extends AbstractCRUDAction<User> {

    @Autowired
    private UserService userService;

    private User user;

    private Date loginBeginTime;

    private Date loginEndTime;

    private List<Integer> ros;

    private List<Integer> grants;

    @Override
    public void prepare() {
        this.bindRequireProperty(userService, user, getQueryMapping());
    }

    @Action(value = INDEX, results = {@Result(name = SUCCESS, location = "../user.jsp")})
    public String index() throws Exception {
        return SUCCESS;
    }

    @Action(value = "repass", results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String repass() throws Exception {
        actionResult = ActionResult.getActionResult(userService.repass(this.user));
        return SUCCESS;
    }

    @Action(value = "grant", results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String grant() throws Exception {
        actionResult = ActionResult.getActionResult(userService.grantRole(this.getIds(), this.ros, this.grants));
        return SUCCESS;
    }

    @Action(value = "role", results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String role() throws Exception {
        actionResult = ActionResult.getActionResult(userService.getGrantRole(this.getUid(), this.ros));
        return SUCCESS;
    }

    private QueryMapping getQueryMapping() {
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
        if (loginBeginTime != null && loginEndTime != null) {
            queryMapping.addBetween("lastTime", new Object[]{loginBeginTime, loginEndTime});
        } else if (loginBeginTime != null) {
            queryMapping.addGt("lastTime", loginBeginTime);
        } else if (loginEndTime != null) {
            queryMapping.addLt("lastTime", loginEndTime);
        }
        return queryMapping;
    }

    @Override
    public User getModel() {
        return user == null ? (user = new User()) : user;
    }

    public List<Integer> getRos() {
        return ros;
    }

    public void setRos(List<Integer> ros) {
        this.ros = ros;
    }

    public List<Integer> getGrants() {
        return grants;
    }

    public void setGrants(List<Integer> grants) {
        this.grants = grants;
    }

    public Date getLoginBeginTime() {
        return loginBeginTime;
    }

    public void setLoginBeginTime(Date loginBeginTime) {
        this.loginBeginTime = loginBeginTime;
    }

    public Date getLoginEndTime() {
        return loginEndTime;
    }

    public void setLoginEndTime(Date loginEndTime) {
        this.loginEndTime = loginEndTime;
    }
}
