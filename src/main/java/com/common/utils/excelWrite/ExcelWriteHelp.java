package com.common.utils.excelWrite;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.io.*;

/**
 * 只支持Execl2007及更高版本的xlsx格式文件的导出
 * @author bocs
 */
public class ExcelWriteHelp {

	/**
	 * 创建工作簿
	 * @return
	 */
	public static SXSSFWorkbook createSXSSFWorkbook() {
		return new SXSSFWorkbook();
	}

	/**
	 * 创建Sheet
	 * @param workbook
	 * @param sheetName
	 * @return
     * 如果需要创建多个sheet，则多次调用该方法后再设置每个sheet的名称
	 */
	public static Sheet createSheet(SXSSFWorkbook workbook, String sheetName) {
		return workbook.createSheet(sheetName);
	}

	/**
	 * 需要将数据导出到excel文件时创建文件输出流
	 * @return
	 */
	public static OutputStream createFileOutputStream(String filePath) {
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return outputStream;
	}

	/**
	 * 将工作簿中的数据写入输出流
	 * @param workbook
	 * @param os
     * 在数据全部写完后执行os.close();关闭文件流。该操作由创建者自己执行
	 */
	public static void saveData(SXSSFWorkbook workbook, OutputStream os) {
		try {
			workbook.write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		workbook.dispose();
	}

	/**注意：
	 * 创建的XSSFCellStyle都会添加在workbook中，所以需要避免使用过多的XSSFCellStyle导致文件很大，尽量采用相同XSSFCellStyle
	 * 需要创建新的XSSFCellStyle时可以使用cloneStyleFrom从现有的XSSFCellStyle复制后再设置其他属性
	 * */

	/**
     * 创建默认样式
     * @param workbook
     * @return
     */
    public static XSSFCellStyle createDefaultCellStyle(SXSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = (XSSFCellStyle) workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }

    /**
     * 创建默认样式
     * @param workbook
     * @return
     */
    public static XSSFCellStyle createDefaultCellStyleWithBorder(SXSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = ExcelWriteHelp.createDefaultCellStyle(workbook);
        initCellBorderStyle(cellStyle);
        return cellStyle;
    }

	/**
	 * 创建标题风格，黑体加粗
	 * @param workbook
	 * @return
	 */
	public static XSSFCellStyle createTitleCellStyle(SXSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = ExcelWriteHelp.createDefaultCellStyle(workbook);
        ExcelWriteHelp.initCellBoldStyle(workbook, cellStyle);
		return cellStyle;
	}

    /**
     * 创建标题风格，黑体加粗，带边框
     * @param workbook
     * @return
     */
    public static XSSFCellStyle createTitleCellStyleWithBorder(SXSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = ExcelWriteHelp.createTitleCellStyle(workbook);
        initCellBorderStyle(cellStyle);
        return cellStyle;
    }

    /**依据给定大小创建cell风格
     * @param workbook
     * @param fontSize
     * @return
     */
    public static XSSFCellStyle createFontCellStyle(SXSSFWorkbook workbook, Short fontSize) {
        XSSFCellStyle titleStyle = ExcelWriteHelp.createDefaultCellStyle(workbook);
        // 生成一个字体
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setFontHeightInPoints(fontSize);
        //使用该字体
        titleStyle.setFont(font);
        return titleStyle;
    }

	/**依据给定大小创建cell风格，黑体加粗
	 * @param workbook
	 * @param fontSize
	 * @return
	 */
	public static XSSFCellStyle createFontBoldCellStyle(SXSSFWorkbook workbook, Short fontSize) {
		XSSFCellStyle titleStyle = ExcelWriteHelp.createDefaultCellStyle(workbook);
		// 生成一个字体
		XSSFFont font = (XSSFFont) workbook.createFont();
		font.setFontHeightInPoints(fontSize);
		font.setBold(true);
		titleStyle.setFont(font);
		return titleStyle;
	}

    /**
     * 给cellStyle添加边框
     * @param cellStyle
     */
    public static void initCellBorderStyle(XSSFCellStyle cellStyle) {
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
    }

    /**
     * 给cellStyle添加黑体加粗风格
     * @param cellStyle
     */
    private static void initCellBoldStyle(SXSSFWorkbook workbook, XSSFCellStyle cellStyle) {
    	//必须创建新的font，不能使用从cellStyle获取的Font，会导致原有使用的风格变化
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);
    }

    /** 备忘
     * 设置单元格的填充效果
     * subTitleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
     * subTitleStyle.setFillForegroundColor(IndexedColors.BLUE1.getIndex());
     */

    /** 依据值类型给cell赋值和设置风格
     * @param workbook
     * @param sheet
     * @param cellStyle
     * @param cell
     * @param value
     */
    public static void buildCellEx(SXSSFWorkbook workbook, Sheet sheet, CellStyle cellStyle, Cell cell, Object value) {
        if( cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
		setCellValue(cell, value);
    }

	/**
	 * 重置cell的值
	 * @param rowIndex
	 * @param colIndex
	 * @param value
	 */
	public static void resetCellValue(Sheet sheet, Integer rowIndex, Integer colIndex, Object value) {
		Row row = sheet.getRow(rowIndex);
		if( row == null) {
			return;
		}
		Cell cell = row.getCell(colIndex);
		if (cell == null) {
			return;
		}
		setCellValue(cell, value);
	}

	/**
	 * 设置cell的值
	 * @param cell
	 * @param value
	 */
    public static void setCellValue(Cell cell, Object value) {
		if (value instanceof Integer) {
			int intValue = (Integer)value;
			cell.setCellValue(intValue);
		} else if (value instanceof Long) {
			long longValue = (Long)value;
			cell.setCellValue(longValue);
		} else if (value instanceof Double) {
			double doubleValue = (Double)value;
			cell.setCellValue(doubleValue);
		} else if (value instanceof Float) {
			float floatValue = (Float)value;
			cell.setCellValue(floatValue);
		} else {
			//其它数据类型都当作字符串简单处理
			String stringValue = "";
			if(value != null) {
				stringValue = value.toString();
			}
			cell.setCellValue(stringValue);
		}
	}

	/** 创建一行，并在这一行中批量创建风格相同的单个cell
	 * startRowIndex: 单元格开始行索引
	 * startColumnIndex: 单元格开始列索引
	 * cellStyle: 所有cell使用相同的风格 */
	public static void buildExcelBatchSingleCell(SXSSFWorkbook workbook, Sheet sheet, int startRowIndex, int startColumnIndex, CellStyle cellStyle, Integer count) {
		for (int i = 0; i < count; i++) {
			buildExcelSingleCell(workbook, sheet, startRowIndex, startColumnIndex + i, cellStyle, null, false);
		}
	}

    /** 创建一行，并在这一行中批量创建风格相同的单个cell并写入数据
     * startRowIndex: 单元格开始行索引
	 * startColumnIndex: 单元格开始列索引
	 * cellStyle: 所有cell使用相同的风格
	 * values各个cell中的值 add by wangwei 20170626 */
	public static void buildExcelBatchSingleCell(SXSSFWorkbook workbook, Sheet sheet, int startRowIndex, int startColumnIndex, CellStyle cellStyle, Object values[]) {
		for (int i = 0; i < values.length; i++) {
			buildExcelSingleCell(workbook, sheet, startRowIndex, startColumnIndex + i, cellStyle, values[i], false);
		}
	}	
	
    /** 创建一行，并在这一行中批量创建风格相同的单个cell并写入数据
     * startRowIndex: 单元格开始行索引
	 * startColumnIndex: 单元格开始列索引
	 * cellStyle: 所有cell使用相同的风格
	 * values各个cell中的值
	 * bNeedResetCellWidth: 是否需要重置cell宽度 add by wangwei 20170626 */
	public static void buildExcelBatchSingleCell(SXSSFWorkbook workbook, Sheet sheet, int startRowIndex, int startColumnIndex, CellStyle cellStyle, Object values[], boolean bNeedResetCellWidth) {
		for (int i = 0; i < values.length; i++) {
			buildExcelSingleCell(workbook, sheet, startRowIndex, startColumnIndex + i, cellStyle, values[i], bNeedResetCellWidth);
		}
	}
	
	/** 创建一行，并在这一行中批量创建传入的风格的单个cell并写入数据
     * startRowIndex: 单元格开始行索引
	 * startColumnIndex: 单元格开始列索引
	 * cellStyles: 各个cell不同的风格 
	 * values各个cell中的值
	 * bNeedResetCellWidth: 是否需要重置cell宽度 add by wangwei 20170626 */
	public static void buildExcelBatchSingleCell(SXSSFWorkbook workbook, Sheet sheet, int startRowIndex, int startColumnIndex, CellStyle cellStyles[], Object values[], boolean bNeedResetCellWidth) {
		for (int i = 0; i < values.length; i++) {
			buildExcelSingleCell(workbook, sheet, startRowIndex, startColumnIndex + i, cellStyles[i], values[i], bNeedResetCellWidth);
		}
	}
    
    /** 创建单个cell并写入数据
     * startRowIndex: 单元格开始行索引
	 * startColumnIndex: 单元格开始列索引
	 * cellStyle: 合并的单元格的风格
	 * value: 合并后的单元格需要显示的值 add by wangwei 20170504 */
	public static void buildExcelSingleCell(SXSSFWorkbook workbook, Sheet sheet, int startRowIndex, int startColumnIndex, CellStyle cellStyle, Object value) {
        Cell cell = createSingleCell(sheet, cellStyle, startRowIndex, startColumnIndex);
        buildCellEx(workbook, sheet, cellStyle, cell, value);
	}
	
	/** 创建单个cell并写入数据
     * startRowIndex: 单元格开始行索引
	 * startColumnIndex: 单元格开始列索引
	 * cellStyle: 合并的单元格的风格
	 * value: 合并后的单元格需要显示的值
	 * bNeedResetCellWidth: 是否需要重置cell宽度 add by wangwei 20170504 */
	public static void buildExcelSingleCell(SXSSFWorkbook workbook, Sheet sheet, int startRowIndex, int startColumnIndex, CellStyle cellStyle, Object value, boolean bNeedResetCellWidth) {
        buildExcelSingleCell(workbook, sheet, startRowIndex, startColumnIndex, cellStyle, value);
        if (bNeedResetCellWidth) {
			resetCellWidth(sheet, startColumnIndex, value);
		}		
	}
    
	/** 创建一行，并在这一行中批量创建风格相同的合并单元格cell并写入数据，每个合并单元格之间没有间隔
     * startRowIndex: 单元格开始行索引
	 * startColumnIndex: 单元格开始列索引
	 * mergedColCnt: 合并的列数
	 * cellStyle: cell的风格，所有cell使用统一个风格
	 * values各个cell中的值 add by wangwei 20170504 */
	public static void buildExcelBatchMergedCell(SXSSFWorkbook workbook, Sheet sheet, int startRowIndex, int startColumnIndex, int mergedColCnt, CellStyle cellStyle, Object values[]) {
		for (int i = 0; i < values.length; i++) {
			buildExcelSingleMergedCell(workbook, sheet, startRowIndex, 1, startColumnIndex + (mergedColCnt * i), mergedColCnt, cellStyle, values[i]);
		}
	}
	
	/** 创建一行，并在这一行中批量创建指定风格的合并单元格cell并写入数据，每个合并单元格之间没有间隔
     * startRowIndex: 单元格开始行索引
	 * startColumnIndex: 单元格开始列索引
	 * mergedColCnt: 合并的列数
	 * cellStyles: 各个cell使用不同的风格
	 * values各个cell中的值 add by wangwei 20170504 */
	public static void buildExcelBatchMergedCell(SXSSFWorkbook workbook, Sheet sheet, int startRowIndex, int startColumnIndex, int mergedColCnt, CellStyle cellStyles[], Object values[]) {
		for (int i = 0; i < values.length; i++) {
			buildExcelSingleMergedCell(workbook, sheet, startRowIndex, 1, startColumnIndex + (mergedColCnt * i), mergedColCnt, cellStyles[i], values[i]);
		}
	}
	
	/** 创建一行，并在这一行中批量创建风格相同的合并单元格cell并写入数据，每个合并单元格之间没有间隔
     * startRowIndex: 单元格开始行索引
	 * startColumnIndex: 单元格开始列索引
	 * mergedRowCnt: 合并的行数
	 * mergedColCnt: 合并的列数
	 * cellStyle: cell的风格
	 * values各个cell中的值 add by wangwei 20170504 */
	public static void buildExcelBatchMergedRowColCell(SXSSFWorkbook workbook, Sheet sheet, int startRowIndex, int startColumnIndex, int mergedRowCnt, int mergedColCnt, CellStyle cellStyle, Object values[]) {
		for (int i = 0; i < values.length; i++) {
			buildExcelSingleMergedCell(workbook, sheet, startRowIndex, mergedRowCnt, startColumnIndex + (mergedColCnt * i), mergedColCnt, cellStyle, values[i]);
		}
	}
	
	/** 创建一行，并在这一行中批量创建指定风格的合并单元格cell并写入数据，每个合并单元格之间没有间隔
     * startRowIndex: 单元格开始行索引
	 * startColumnIndex: 单元格开始列索引
	 * mergedRowCnt: 合并的行数
	 * mergedColCnt: 合并的列数
	 * cellStyles: cell的风格，不同cell使用不同风格
	 * values各个cell中的值 add by wangwei 20170504 */
	public static void buildExcelBatchMergedRowColCell(SXSSFWorkbook workbook, Sheet sheet, int startRowIndex, int startColumnIndex, int mergedRowCnt, int mergedColCnt,  CellStyle cellStyles[], Object values[]) {
		for (int i = 0; i < values.length; i++) {
			buildExcelSingleMergedCell(workbook, sheet, startRowIndex, mergedRowCnt, startColumnIndex + (mergedColCnt * i), mergedColCnt, cellStyles[i], values[i]);
		}
	}
	
    /** 创建单个合并的cell并写入数据
     * startRowIndex: 单元格开始行索引
	 * mergedRowCnt: 需要合并的行数
	 * startColumnIndex: 单元格开始列索引
	 * mergedColCnt 需要合并的列数 
	 * cellStyle: 合并的单元格的风格
	 * value: 合并后的单元格需要显示的值 add by wangwei 20170504 */
	public static void buildExcelSingleMergedCell(SXSSFWorkbook workbook, Sheet sheet, int startRowIndex, int mergedRowCnt, int startColumnIndex, int mergedColCnt, CellStyle cellStyle, Object value) {
		if (mergedRowCnt > 1 || mergedColCnt > 1) {	
			/* Excel从0计数，起始索引startRowIndex是0，mergedRowCnt要合并3个cell，则结束索引值为2，列同理 */
			int lastRowIndex = startRowIndex + mergedRowCnt - 1;
			int lastColIndex = startColumnIndex + mergedColCnt - 1;
			/* 循环创建出合并单元格中的所有cell，并设置风格，使整个合并后的单元格具有指定的风格 */
			for (int rowIndex = startRowIndex; rowIndex <= lastRowIndex; rowIndex++) {
				for (int colIndex = startColumnIndex; colIndex <= lastColIndex; colIndex++) {
					createSingleCell(sheet, cellStyle, rowIndex, colIndex);
				}
			}
			/* 将创建出来的cell合并 */
			createMergedCell(sheet, startRowIndex, lastRowIndex, startColumnIndex, lastColIndex);
			/* 获取合并后的单元格的左上角第一个cell为整个合并后的单元格赋值，false表示对于合并单元格不重置cell宽度，避免对后续cell有影响 */
			buildExcelSingleCell(workbook, sheet, startRowIndex, startColumnIndex, cellStyle, value, false);			
		} else {
			/* 如果传入的合并的行数和列数都小等于1，表明当前创建的是单个cell */
			buildExcelSingleCell(workbook, sheet, startRowIndex, startColumnIndex, cellStyle, value);			
		}
	}

    /**
     * 依据传入的多个创建CreateCellInfo创建cell，CreateCellInfo中的信息标记处当前需要创建的cell是否需要合并 add by wangwei 20170504
     * @param workbook
     * @param sheet
     * @param cellInfoArray
     */
    public static void buildExcelByCellInfo(SXSSFWorkbook workbook, Sheet sheet, CreateCellInfo cellInfoArray[]) {
        for (CreateCellInfo cellInfo : cellInfoArray) {
            buildExcelSingleMergedCell(workbook, sheet, cellInfo.getStartRow(), cellInfo.getMergedRowCnt(),
                    cellInfo.getStartCol(), cellInfo.getMergedColCnt(), cellInfo.getCellStyle(),
                    cellInfo.getCellValue());
        }
    }

    /**
     * 按照给定的行列下表创建单个cell，在没有获取到row或者cell时表明还没有创建，再调用创建方法 add by wangwei 20170504
     * @param sheet
     * @param cellStyle
     * @param rowIndex
     * @param colIndex
     * @return
     */
    private static Cell createSingleCell(Sheet sheet, CellStyle cellStyle, int rowIndex, int colIndex) {
		Row row = sheet.getRow(rowIndex);
    	if( row == null) {
            row = sheet.createRow(rowIndex);
        }
    	Cell cell = row.getCell(colIndex);
    	if (cell == null) {
    		cell = row.createCell(colIndex);
    	}
    	if (cellStyle != null) {
    		cell.setCellStyle(cellStyle);
    	}
    	return cell;
    }       
    
    /** 创建一个cell合并单元格
     * 设定合并单元格区域范围 
     * firstRow  0-based 
     * lastRow   0-based 
     * firstCol  0-based 
     * lastCol   0-based add by wangwei 20170504 */
    private static void createMergedCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
    	CellRangeAddress cra = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);        
    	sheet.addMergedRegion(cra);
    }
    
    /** 设置cell宽度，原有实现中没有替换成该函数，只对新添加的函数使用
     * poi接口默认以一个字符的1/256的宽度作为一个单位 add by wangwei20170504 */
    private static void resetCellWidth(Sheet sheet, int colIndex, Object value) {
    	int currentColumnWidth = sheet.getColumnWidth(colIndex);
        int cellWidth = 0;
		if (value != null) {
			try {
				/* 因为标题中有中文，优先按gbk字符集计算大小 */
				cellWidth = (value.toString().getBytes("gbk").length + 2) * 256;
			} catch (UnsupportedEncodingException e) {
				/* 出现异常时按默认字符集计算大小 */
				cellWidth = (value.toString().getBytes().length + 2) * 256;
			}
		}
		if( cellWidth > currentColumnWidth) {
            sheet.setColumnWidth(colIndex, cellWidth);                    
        }
    }
}
