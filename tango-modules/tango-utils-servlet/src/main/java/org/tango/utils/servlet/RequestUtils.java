package org.tango.utils.servlet;

import org.apache.commons.lang.StringUtils;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * User: tango
 * Date: 13-12-7
 * Time: 下午7:21
 */
public final class RequestUtils {
    /**
     * 获取远程IP地址
     *
     * @param request HttpServletRequest
     * @return 远程IP地址
     */
    public final static String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader(" x-forwarded-for ");
        String unknown = " unknown ";
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(" Proxy-Client-IP ");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getHeader(" WL-Proxy-Client-IP ");
        }
        if (StringUtils.isEmpty(ip) || unknown.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取远程Mac地址
     *
     * @param request HttpServletRequest
     * @return 远程Mac地址
     */
    public static String getRemoteMacAddress(HttpServletRequest request) {
        //TODO: 获取远程Mac地址
        return "0";
    }

    /**
     * 获取El表达式值
     *
     * @param expression
     * @param valueType
     * @param httpServlet
     * @param request
     * @param response
     * @return
     */
    Object parseEL(String expression, Class<?> valueType, HttpServlet httpServlet, ServletRequest request, ServletResponse response) {
        JspFactory jspFactory = JspFactory.getDefaultFactory();
        final ExpressionFactory exprFactory = jspFactory.getJspApplicationContext(httpServlet.getServletContext()).getExpressionFactory();
        PageContext pageContext = jspFactory.getPageContext(httpServlet, request, response, null, true, 8192, true);
        //
        ELContext elContext = pageContext.getELContext();

        ValueExpression ve = exprFactory.createValueExpression(//
                elContext,//
                "${" + expression + "}",//
                valueType);
        return ve.getValue(elContext);
    }

    /**
     * 获取 HttpServletRequest 参数值
     *
     * @param request 请求对象
     * @param name    参数名
     * @return 参数值
     */
    public static final String getParameter(HttpServletRequest request, String name) {
        return request.getParameter(name);
    }

    /**
     * 获取 HttpServletRequest 属性值
     *
     * @param request 请求对象
     * @param name    参数名
     * @return 属性值
     */
    public final Object getRequestAttribute(HttpServletRequest request, String name) {
        return request.getAttribute(name);
    }

    /**
     * 获取 HttpServletRequest 属性值
     *
     * @param request 请求对象
     * @param name    参数名
     * @param clazz   返回值类型
     * @return 属性值
     */
    public final <T> T getRequestAttribute(HttpServletRequest request, String name, Class<T> clazz) {
        T result = null;
        Object object = request.getAttribute(name);
        try {
            result = (T) object;
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 设置 HttpServletRequest 属性值
     *
     * @param request 请求对象
     * @param name    属性名
     * @param value   属性值
     */
    public final void setRequestAttribute(HttpServletRequest request, String name, Object value) {
        request.setAttribute(name, value);
    }

    /**
     * 获取上下文路径
     *
     * @param request 请求对象
     * @return 上下文路径
     */
    public final String getContextPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    /**
     * 获取完整上下文路径
     *
     * @param request 请求对象
     * @return 完整上下文路径
     */
    public final String getFullContextPath(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme()).append("://")//
                .append(request.getServerName()).append(":").append(request.getServerPort())//
                .append(request.getContextPath()).append("/");//
        return builder.toString();
    }

    /**
     * 获取请求远程地址
     *
     * @param request 请求对象
     * @return 请求远程地址
     */
    public final String getRequestAddress(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
