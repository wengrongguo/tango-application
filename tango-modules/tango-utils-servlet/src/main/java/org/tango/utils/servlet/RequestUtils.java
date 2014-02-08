package org.tango.utils.servlet;

import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

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
}
