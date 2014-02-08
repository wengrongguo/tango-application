package org.tango.struts.action;

import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.tango.core.abs.AbstractModel;
import org.tango.core.abs.AbstractService;
import org.tango.core.bean.Pager;
import org.tango.core.bean.QueryMapping;
import org.tango.struts.bean.ActionResult;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import static org.tango.core.constant.StrutsConstant.*;

/**
 * User: tango
 * Date: 13-6-13
 * Time: 下午3:05
 */
public abstract class AbstractCRUDAction<T extends AbstractModel> extends AbstractAction implements ModelDriven<T> {

    private AbstractService abstractService;

    private AbstractModel abstractModel;

    private QueryMapping queryMapping;

    private List<AbstractModel> models;

    protected Class<?> clazz = null;

    protected AbstractCRUDAction() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.clazz = (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

    @Action(value = DETAIL, results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String detail() throws Exception {
        prepare();
        actionResult = ActionResult.getActionResult(abstractService.get(this.getUid()));
        return SUCCESS;
    }

    @Action(value = LIST, results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String list() throws Exception {
        prepare();
        actionResult = ActionResult.getActionResult(abstractService.getList(queryMapping));
        return SUCCESS;
    }

    @Action(value = PAGE, results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT_SINGLETON_OBJECT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String page() throws Exception {
        prepare();
        actionResult = ActionResult.getActionResult(abstractService.getListPage(new Pager(this.getPage(), this.getRows(), true), queryMapping));
        return SUCCESS;
    }

    @Action(value = SAVE_OR_UPDATE, results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String saveOrUpdate() throws Exception {
        try {
            prepare();
            boolean temp = false;
            if (abstractModel.getId() != null) {
                temp = abstractService.updateNotNull(abstractModel);
            } else {
                temp = abstractService.save(abstractModel);
            }
            actionResult = ActionResult.getActionResult(temp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SUCCESS;
    }

    @Action(value = DELETE, results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String delete() throws Exception {
        prepare();
        actionResult = ActionResult.getActionResult(abstractService.delete(this.getUid()));
        return SUCCESS;
    }

    @Action(value = DELETES, results = {@Result(name = SUCCESS, type = JSON, params = {ROOT, ACTION_RESULT, CONTENT_TYPE, CONTENT_TYPE_HTML})})
    public String deletes() throws Exception {
        prepare();
        actionResult = ActionResult.getActionResult(abstractService.deletes(this.getIds()));
        return SUCCESS;
    }

    protected void bindRequireProperty(AbstractService abstractService, AbstractModel abstractModel) {
        this.abstractService = abstractService;
        this.abstractModel = abstractModel;
    }

    protected void bindRequireProperty(AbstractService abstractService, AbstractModel abstractModel, QueryMapping queryMapping) {
        this.bindRequireProperty(abstractService, abstractModel);
        this.queryMapping = queryMapping;
    }


    protected abstract void prepare();

}
