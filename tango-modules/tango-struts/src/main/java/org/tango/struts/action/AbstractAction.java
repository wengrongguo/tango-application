package org.tango.struts.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.tango.struts.bean.ActionResult;

import java.util.List;

/**
 * User: tango
 * Date: 13-5-20
 * Time: 下午3:05
 */
public abstract class AbstractAction extends ActionSupport {

    private static final Log log = LogFactory.getLog(AbstractAction.class);

    protected ActionResult actionResult;
    //
    private String uuid;
    private Integer uid;
    private int page = 1;
    private int rows = 10;
    private List<Integer> ids;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public ActionResult getActionResult() {
        return actionResult;
    }

    public void setActionResult(ActionResult actionResult) {
        this.actionResult = actionResult;
    }
}
