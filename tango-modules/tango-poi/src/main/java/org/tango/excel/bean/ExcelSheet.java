package org.tango.excel.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tango on 14-1-29.
 */
public class ExcelSheet {

    private String name;

    private ExcelRow header;

    private List<ExcelRow> rows = new ArrayList<ExcelRow>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExcelRow getHeader() {
        return header;
    }

    public void setHeader(ExcelRow header) {
        this.header = header;
    }

    public List<ExcelRow> getRows() {
        return rows;
    }

    public void addRow(ExcelRow row) {
        rows.add(row);
    }

    @Override
    public String toString() {
        return "ExcelSheet{" +
                "name='" + name + '\'' +
                ", header=" + header +
                ", rows=" + rows +
                '}';
    }
}
