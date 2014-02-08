package org.tango.framework.action;

import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.tango.core.bean.QueryMapping;
import org.tango.core.constant.NumberConstant;
import org.tango.framework.action.vo.MenuVo;
import org.tango.framework.domain.Menu;
import org.tango.framework.service.MenuService;
import org.tango.struts.action.AbstractCRUDAction;
import org.tango.struts.bean.ActionResult;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
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
@Namespace("/system/menu")
@ParentPackage(ADMIN_DEFAULT)
public class MenuAction extends AbstractCRUDAction<Menu> {

    @Autowired
    private MenuService menuService;

    private Menu menu;

    @Override
    public void prepare() {
        this.bindRequireProperty(menuService, menu);
    }

    @Action(value = INDEX, results = {@Result(name = SUCCESS, location = "../menu.jsp")})
    public String index() throws Exception {
        return SUCCESS;
    }

    @Action(value = TREE, results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String tree() throws Exception {
        if (this.getUid() == null) {
            this.setUid(NumberConstant.ZERO);
        }
        List<Menu> menusByParentId = menuService.getList(new QueryMapping().addEquals("parentMenu", this.menu.getId() != null ? this.menu.getId() : this.getUid()).addOrder("orderNum", true));
        List<MenuVo> menuVos = new ArrayList<MenuVo>();
        for (Iterator<Menu> iterator = menusByParentId.iterator(); iterator.hasNext(); ) {
            Menu next = iterator.next();
            int total = menuService.count(new QueryMapping().addEquals("parentMenu", next.getId()));
            MenuVo menuVo = MenuVo.parse(next, total);
            menuVos.add(menuVo);
        }
        actionResult = ActionResult.getActionResult(menuVos);
        return SUCCESS;
    }

    @Override
    public Menu getModel() {
        return menu == null ? (menu = new Menu()) : menu;
    }
}
