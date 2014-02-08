package org.tango.framework.service;

import org.tango.core.abs.AbstractService;
import org.tango.core.bean.QueryMapping;
import org.tango.framework.domain.LoginLog;

import java.io.ByteArrayOutputStream;

/**
 * 登陆日志接口
 * User: tAngo
 * Date: 12-12-21
 * Time: 上午8:52
 */
public interface LoginLogService extends AbstractService<LoginLog> {

    ByteArrayOutputStream exportExcel(QueryMapping queryMapping);
}
