package org.tango.excel.bean;

/**
 * Created by tango on 14-1-29.
 */
public class ExcelCell {

    public ExcelCell() {
    }

    public ExcelCell(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "{" +
                "value=" + value +
                '}';
    }
}
