/*
 * Copyright (c) 2012. tAngo
 * 	Email : org.java.tango@gmail.com
 */

package org.tango.framework.service.impl;

import org.springframework.stereotype.Service;
import org.tango.core.bean.QueryMapping;
import org.tango.core.constant.Constant;
import org.tango.excel.bean.ExcelAdapter;
import org.tango.excel.bean.ExcelRow;
import org.tango.excel.bean.ExcelSheet;
import org.tango.framework.domain.LoginLog;
import org.tango.framework.domain.Menu;
import org.tango.framework.service.LoginLogService;
import org.tango.framework.service.MenuService;
import org.tango.utils.DateUtils;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * 登陆日志实现类
 * User: tAngo
 * Date: 12-11-11
 * Time: 下午4:47
 */
@Service
public class LoginLogServiceImpl extends FrameworkServiceSupport<LoginLog> implements LoginLogService {

    private ExcelAdapter excelAdapter = new ExcelAdapter();

    @Override
    public ByteArrayOutputStream exportExcel(QueryMapping queryMapping) {
        List<LoginLog> logs = this.getList(queryMapping);
        ExcelSheet[] excelSheets = getExcelSheets(logs);
        ByteArrayOutputStream byteArrayOutputStream = excelAdapter.toStream(excelSheets);
        return byteArrayOutputStream;
    }

    private ExcelSheet[] getExcelSheets(List<LoginLog> logs) {
        int row = 0;
        //
        ExcelSheet excelSheet = new ExcelSheet();
        excelSheet.setName("login log");
        ExcelRow header = new ExcelRow("ID", "账号", "时间", "IP", "状态", "类型");
        header.setRowNumber(row++);
        excelSheet.setHeader(header);

        for (Iterator<LoginLog> iterator = logs.iterator(); iterator.hasNext(); ) {
            LoginLog loginLog = iterator.next();
            ExcelRow excelRow = new ExcelRow(
                    loginLog.getId().toString(),
                    loginLog.getAccount(),
                    DateUtils.format(loginLog.getDate()), loginLog.getIp(),
                    "1".equals(loginLog.getStated()) ? "成功" : "失败",
                    1 == loginLog.getType() ? "登录" : "登出");
            excelRow.setRowNumber(row++);
            excelSheet.addRow(excelRow);
        }
        return new ExcelSheet[]{excelSheet};
    }

}
