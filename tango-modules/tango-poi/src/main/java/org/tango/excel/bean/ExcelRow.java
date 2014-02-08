package org.tango.excel.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by tango on 14-1-29.
 */
public class ExcelRow {

    private long rowNumber;

    private List<ExcelCell> cells;

    private boolean isEnd = false;

    public ExcelRow() {
    }

    public ExcelRow(String ... cs) {
        cells = new ArrayList<ExcelCell>();
        for (int i = 0; i < cs.length; i++) {
            String c = cs[i];
            cells.add(new ExcelCell(c));
        }
    }

    public long getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(long rowNumber) {
        this.rowNumber = rowNumber;
    }

    public List<ExcelCell> getCells() {
        return cells;
    }

    public void setCells(List<ExcelCell> cells) {
        this.cells = cells;
    }

    @Override
    public String toString() {
        return "ExcelRow{" +
                "rowNumber=" + rowNumber +
                ", cells=" + cells +
                '}';
    }

    public boolean isVoid() {
        return this.cells.size() == 0;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }
}
