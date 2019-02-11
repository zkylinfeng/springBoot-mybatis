/*
 * Copyright (c) 2017 BOC Services（Kunshan）Co.,Ltd.
 */

/**
 * BaseSheetContentsHandler.java
 */
package com.common.utils.excelRead;

import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseSheetContentsHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

    private static Logger logger = LoggerFactory.getLogger(BaseSheetContentsHandler.class);

    private int currentRow = -1;
    private int currentCol = -1;
    private List<String> rowValues;

    @Override
    public void startRow(int rowNum) {
        this.currentRow = rowNum;
        this.currentCol = -1;
        this.rowValues = new ArrayList<>();
    }

    protected abstract void onParseRow(int rowNum, List<String> rowValues);

    @Override
    public void endRow(int rowNum) {
        this.onParseRow(rowNum, rowValues);
    }

    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
        if (cellReference == null) {
            cellReference = new CellAddress(currentRow, currentCol).formatAsString();
        }
        int thisCol = (new CellReference(cellReference)).getCol();
        int missedCols = thisCol - currentCol - 1;//处理数据中间存在空白
        for (int i = 0; i < missedCols; i++) {
            this.rowValues.add("");
        }
        this.rowValues.add(formattedValue);
        currentCol = thisCol;
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
        logger.debug("{}: {}, tag: {}", isHeader ? "Header" : "Footer", text, tagName);
    }
}
