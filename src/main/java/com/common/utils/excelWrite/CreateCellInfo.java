package com.common.utils.excelWrite;

import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;



/**
 * 用于创建cell的基本信息
 * @author bocs
 */
@Data
public class CreateCellInfo {
	private int startRow = 0; //创建的cell所在的开始行索引值，从0开始
	private int startCol = 0; //创建的cell所在的开始列索引值，从0开始
	private int mergedRowCnt = 1; //合并的单元格的行数，默认为1表示一个单元格
	private int mergedColCnt = 1; //合并的单元格的列数，默认为1表示一个单元格
	private HSSFCellStyle cellStyle = null; //当前创建的cell需要使用的风格
	private Object cellValue = null; //创建的cell中的值
	
	public CreateCellInfo(int startRow, int mergedRowCnt, int startCol, int mergedColCnt, HSSFCellStyle cellStyle, Object cellValue) {
		this.startRow = startRow;
		this.startCol = startCol;
		this.mergedRowCnt = mergedRowCnt;
		this.mergedColCnt = mergedColCnt;
		this.cellStyle = cellStyle;
		this.cellValue = cellValue;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	public int getMergedRowCnt() {
		return mergedRowCnt;
	}

	public void setMergedRowCnt(int mergedRowCnt) {
		this.mergedRowCnt = mergedRowCnt;
	}

	public int getMergedColCnt() {
		return mergedColCnt;
	}

	public void setMergedColCnt(int mergedColCnt) {
		this.mergedColCnt = mergedColCnt;
	}

	public HSSFCellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(HSSFCellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}

	public Object getCellValue() {
		return cellValue;
	}

	public void setCellValue(Object cellValue) {
		this.cellValue = cellValue;
	}
}
