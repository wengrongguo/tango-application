package org.tango.framework.action;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.tango.core.annotation.Access;
import org.tango.core.bean.QueryMapping;
import org.tango.framework.domain.LoginLog;
import org.tango.framework.service.LoginLogService;
import org.tango.struts.action.AbstractCRUDAction;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;

import static org.tango.core.constant.StrutsConstant.*;

/**
 * User: tango
 * Date: 13-6-13
 * Time: 下午2:13
 */
@Controller
@Scope(PROTOTYPE)
@Namespace("/system/loginlog")
@ParentPackage(ADMIN_DEFAULT)
public class LoginLogAction extends AbstractCRUDAction<LoginLog> {

    @Autowired
    private LoginLogService loginLogService;

    private LoginLog loginLog;

    private Date loginBeginTime;

    private Date loginEndTime;

    @Override
    public void prepare() {
        this.bindRequireProperty(loginLogService, loginLog, getQueryMapping());
    }

    @Action(value = INDEX, results = {@Result(name = SUCCESS, location = "../loginlog.jsp")})
    public String index() throws Exception {
        return SUCCESS;
    }

    private String downloadName;

    private InputStream stream;

    @Access(login = true, privilege = false)
    @Action(value = "export",results = {@Result(name = SUCCESS, type = "stream",
            params = {"contentType", "application/vnd.ms-excel", "inputName", "stream", "contentDisposition", "attachment;filename=${downloadName}.xls", "bufferSize", "1024"})
    })
    public String export() throws Exception {
        ByteArrayOutputStream byteArrayOutputStream =  loginLogService.exportExcel(getQueryMapping());
        stream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        downloadName = "login-log";
        return SUCCESS;
    }

    private QueryMapping getQueryMapping() {
        QueryMapping queryMapping = new QueryMapping();
        if (!StringUtils.isBlank(loginLog.getUserId())) {
            queryMapping.addEquals("userId", loginLog.getUserId());
        }
        if (!StringUtils.isBlank(loginLog.getAccount())) {
            queryMapping.addEquals("account", loginLog.getAccount());
        }
        if (!StringUtils.isBlank(loginLog.getIp())) {
            queryMapping.addEquals("ip", loginLog.getIp());
        }
        if (loginLog.getType() != null) {
            queryMapping.addEquals("type", loginLog.getType());
        }
        if (loginBeginTime != null && loginEndTime != null) {
            queryMapping.addBetween("date", new Object[]{loginBeginTime, loginEndTime});
        } else if (loginBeginTime != null) {
            queryMapping.addGt("date", loginBeginTime);
        } else if (loginEndTime != null) {
            queryMapping.addLt("date", loginEndTime);
        }
        return queryMapping;
    }

    @Override
    public LoginLog getModel() {
        return loginLog == null ? (loginLog = new LoginLog()) : loginLog;
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

    public String getDownloadName() {
        return downloadName;
    }

    public void setDownloadName(String downloadName) {
        this.downloadName = downloadName;
    }

    public InputStream getStream() {
        return stream;
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }
}
