package org.tango.struts.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.util.ReflectionUtils;
import org.tango.core.annotation.Access;
import org.tango.core.cache.LRUCache;
import org.tango.core.exception.LoginException;
import org.tango.core.exception.PrivilegeException;
import org.tango.struts.action.AbstractAction;
import org.tango.struts.bean.ActionResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import static org.tango.core.constant.StrutsConstant.ERROR;

/**
 * User: tAngo
 * Date: 13-1-3
 * Time: 下午8:47
 */
public abstract class AbstractPrivilegeInterceptor extends com.opensymphony.xwork2.interceptor.AbstractInterceptor {

    protected LRUCache<String, Access> lruCache = null;

    protected final static Log log = LogFactory.getLog(AbstractPrivilegeInterceptor.class);

    private final static String ERROR_JSON = "errorJson";

    private final static String ERROR_PAGE = "errorPage";

    protected String login = "login.action";

    private boolean interceptLogin = true;

    private boolean interceptPrivilege = true;

    private int capacity = 100;

    @Override
    public void init() {
        lruCache = new LRUCache<String, Access>(capacity);
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionSupport actionSupport = (ActionSupport) invocation.getAction();
        HttpServletRequest request = ServletActionContext.getRequest();
        String result = null;
        //
        log4Request(invocation, request);
        try {
            if (interceptLogin) {
                interceptLogin(request, invocation, actionSupport);
                if (this.isGet(request) && this.interceptPrivilege) {
                    interceptPrivilege(request, invocation, actionSupport);
                }
            }
        } catch (LoginException e) {
            result = sendError(actionSupport, request, e, "system.error.undefined.login", ActionSupport.LOGIN, ERROR_JSON);
        } catch (PrivilegeException e) {
            result = sendError(actionSupport, request, e, "system.error.grant.privilege", "privilege", ERROR_JSON);
        } catch (Exception e) {
            result = sendError(actionSupport, request, e, "system.error.unknow", ERROR_PAGE, ERROR_JSON);
        }
        try {
            if (result == null) {
                result = invocation.invoke();
            } else {
                return result;
            }
        } catch (LoginException e) {
            result = sendError(actionSupport, request, e, "system.error.login.".concat(e.getMessage()), ERROR_PAGE, ERROR_JSON);
        } catch (SQLException e) {
            result = sendError(actionSupport, request, e, "system.error.sql", ERROR_PAGE, ERROR_JSON);
        } catch (Exception e) {
            if (e.getMessage().indexOf("java.sql.SQLException") > 0) {
                result = sendError(actionSupport, request, e, "system.error.sql", ERROR_PAGE, ERROR_JSON);
            } else {
                String errorMessage = e.getMessage();
                String i18n = actionSupport.getText(errorMessage);
                if (!i18n.equals(errorMessage)) {
                    result = sendError(actionSupport, request, e, i18n, ERROR_PAGE, ERROR_JSON);
                } else {
                    result = sendError(actionSupport, request, e, "system.error.unknow", ERROR_PAGE, ERROR_JSON);
                }
            }
        }
        log4Response(invocation, result, request);
        return result;
    }

    private String sendError(ActionSupport actionSupport, HttpServletRequest request, Exception e, String message, String getErrorForward, String jsonErrorForward) throws Exception {
        String result = null;
        //
        log.error(e);
        String messageContent = actionSupport.getText(message);
        if (isGet(request)) {
            actionSupport.addActionMessage(messageContent);
            result = getErrorForward;
        } else {
            if (actionSupport instanceof AbstractAction) {
                AbstractAction action = ((AbstractAction) actionSupport);
                action.setActionResult(ActionResult.getActionResult(ERROR, messageContent));
            }
            result = jsonErrorForward;
        }
        return result;
    }

    /*
     * 请求Log
     */
    private void log4Request(ActionInvocation invocation, HttpServletRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("[/--------------[请求信息如下:]--------------/");
            log.debug(new MessageFormat("请求 - 地址: {0}").format(new Object[]{request.getRequestURI()}));
            log.debug(new MessageFormat("请求 - Action: {0}").format(new Object[]{invocation.getAction()}));
            log.debug("请求 - Param: ");
            Map<String, Object> parameters = ActionContext.getContext().getParameters();
            for (Iterator<Map.Entry<String, Object>> iterator = parameters.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iterator.next();
                Object value = entry.getValue();
                if (value instanceof String[]) {
                    value = Arrays.toString((String[]) value);
                }
                log.debug(new MessageFormat("name : {0},value : {1}").format(new Object[]{entry.getKey(), value}));
            }
            log.debug("[/--------------------------------------------/");
        }
    }

    /*
     * 响应Log
     */
    private void log4Response(ActionInvocation invocation, String result, HttpServletRequest request) {
        if (log.isDebugEnabled()) {
            Map<String, ResultConfig> resultConfigMap = invocation.getProxy().getConfig().getResults();
            log.debug("[/--------------[响应信息如下:]--------------/");
            if (resultConfigMap.containsKey(result)) {
                ResultConfig resultConfig = resultConfigMap.get(result);
                Map<String, String> resultConfigParams = resultConfig.getParams();
                log.debug(new MessageFormat("响应 - ResultConfig: {0},Param : {1}").format(new Object[]{resultConfig.getName(), resultConfigParams}));
                ActionSupport actionSupport = (ActionSupport) invocation.getAction();
                if (resultConfigParams.containsKey("root") && (actionSupport instanceof AbstractAction)) {
                    log.debug(new MessageFormat("响应 - Result : {0}").format(new Object[]{((AbstractAction) actionSupport).getActionResult()}));
                }
            } else {
                log.debug(new MessageFormat("未找到ActionResult: {0}").format(new Object[]{result}));
            }
            log.debug("[/--------------------------------------------/");
        }
    }

    /*
     * 拦截登陆
     */
    public abstract void interceptLogin(HttpServletRequest request, ActionInvocation invocation, ActionSupport actionSupport);


    /*
     * 拦截权限
     */
    public abstract void interceptPrivilege(HttpServletRequest request, ActionInvocation invocation, ActionSupport actionSupport) throws ServletException, IOException;

    /*
     * 是否为get请求
     */
    private boolean isGet(HttpServletRequest request) {
        String requestMethod = request.getMethod();
        return "get".equalsIgnoreCase(requestMethod);
    }

    protected Access getAccessInLRU(ActionInvocation invocation) {
        ActionProxy actionProxy = invocation.getProxy();
        String methodName = actionProxy.getMethod();
        //
        String key = actionProxy.getActionName().concat("@").concat(methodName);
        if (lruCache.containsKey(key)) {
            return lruCache.get(key);
        }
        Method method = ReflectionUtils.findMethod(invocation.getAction().getClass(), methodName);
        Access access = method.getAnnotation(Access.class);
        lruCache.put(key, access);
        return access;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isInterceptLogin() {
        return interceptLogin;
    }

    public void setInterceptLogin(boolean interceptLogin) {
        this.interceptLogin = interceptLogin;
    }

    public boolean isInterceptPrivilege() {
        return interceptPrivilege;
    }

    public void setInterceptPrivilege(boolean interceptPrivilege) {
        this.interceptPrivilege = interceptPrivilege;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
