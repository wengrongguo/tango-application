package org.tango.excel.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.tango.utils.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by tango on 14-1-29.
 */
public class ExcelAdapter {

    private static final Log log = LogFactory.getLog(ExcelAdapter.class);

    public ByteArrayOutputStream toStream(ExcelSheet... sheets) {

        Workbook workbook = new HSSFWorkbook();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
//
            for (int i = 0; i < sheets.length; i++) {
                int r = 0, j = 0;
                ExcelSheet excelSheet = sheets[i];
                Sheet sheet = workbook.createSheet(excelSheet.getName());
                //
                ExcelRow header = excelSheet.getHeader();
                Row headerRow = sheet.createRow(r++);
                for (Iterator<ExcelCell> iterator = header.getCells().iterator(); iterator.hasNext(); ) {
                    ExcelCell excelCell = iterator.next();
                    Cell cell = headerRow.createCell(j++);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellValue(excelCell.getValue());
                }
                for (Iterator<ExcelRow> iterator = excelSheet.getRows().iterator(); iterator.hasNext(); ) {
                    ExcelRow excelRow = iterator.next();
                    j = 0;
                    Row row = sheet.createRow(r++);
                    for (Iterator<ExcelCell> cellIterator = excelRow.getCells().iterator(); cellIterator.hasNext(); ) {
                        ExcelCell excelCell = cellIterator.next();
                        Cell cell = row.createCell(j++);
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        cell.setCellValue(excelCell.getValue());
                    }
                }
                workbook.write(outputStream);
            }
        } catch (Exception e) {
            log.error(StringUtils.format("the adapter error of toStream excel,message:[{0}]", e.getMessage()));
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException ig) {

                }
            }
        }
        return outputStream;
    }

}
