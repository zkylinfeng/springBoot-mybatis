/*
 * Copyright (c) 2017 BOC Services（Kunshan）Co.,Ltd.
 */

/**
 * ExcelHelp.java
 */
package com.common.utils.excelRead;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.SAXHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelReadHelp {
    private static Logger logger = LoggerFactory.getLogger(ExcelReadHelp.class);
    private static DecimalFormat df = new DecimalFormat("#.#########");
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    /**
     * SAX方式读取excel文件
     * @param file    文件
     * @param handler 处理类
     */
    public static void readExcelSAX(File file, BaseSheetContentsHandler handler) throws Exception {
        String filename = file.getName();
        if (StringUtils.isEmpty(filename)) {
            return;
        }
        if (filename.endsWith("xls") || filename.endsWith("xlsx") || filename.endsWith("csv")) {
            if (filename.endsWith("xlsx")) {
                //Excel2007以后采用SAX方式
                try (OPCPackage pkg = OPCPackage.open(file)) {
                    excelReadSAX(pkg, handler);
                } catch (InvalidFormatException e) {
                    logger.debug("错误的文件格式 filename: {}", filename);
                    throw new Exception("文件格式错误!");
                } catch (IOException | OpenXML4JException | SAXException | ParserConfigurationException e) {
                    logger.error("Excle读取失败, filename: {}", filename, e);
                    throw e;
                }
            } else {
                //Excel2007以前采用传统方式
                try (InputStream is = new FileInputStream(file)) {
                    excelRead(filename, is, handler);
                } catch (IOException e) {
                    logger.error("Excle文件打开失败, file: {}", file.getAbsolutePath(), e);
                    throw e;
                }
            }
        } else {
            logger.debug("filename: {}", filename);
            throw new Exception("暂不支持此类型文件!");
        }
    }

    /**
     * SAX方式读取Excel文件
     * @param filename 文件名
     * @param is       输入流
     * @param handler  处理类
     */
    public static void readExcelSAX(String filename, InputStream is, BaseSheetContentsHandler handler) throws Exception {
        if (StringUtils.isEmpty(filename)) {
            return;
        }
        if (filename.endsWith("xls") || filename.endsWith("xlsx")) {
            if (filename.endsWith("xlsx")) {
                //Excel2007以后采用SAX方式
                try (OPCPackage pkg = OPCPackage.open(is)) {
                    excelReadSAX(pkg, handler);
                } catch (InvalidFormatException e) {
                    logger.debug("错误的文件格式 filename: {}", filename);
                    throw new Exception("文件格式错误!");
                } catch (IOException | OpenXML4JException | SAXException | ParserConfigurationException e) {
                    logger.error("Excle 读取失败, filename: {}", filename, e);
                    throw e;
                }
            } else {
                //Excel2007以前采用传统方式
                excelRead(filename, is, handler);
            }
        } else {
            logger.debug("暂不支持此类型文件, filename: {}", filename);
            throw new Exception("暂不支持此类型文件!");
        }
    }

    /**
     * SAX流式读取文件
     * @param pkg
     * @param handler
     * @throws IOException
     * @throws OpenXML4JException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    private static void excelReadSAX(OPCPackage pkg, BaseSheetContentsHandler handler) throws IOException, OpenXML4JException, SAXException, ParserConfigurationException {
        XSSFReader reader = new XSSFReader(pkg);
        StylesTable stylesTable = reader.getStylesTable();
        ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(pkg);
        XMLReader sheetParser = ExcelReadHelp.fetchSheetParser(stylesTable, strings, handler);
        sheetParser.parse(new InputSource(reader.getSheetsData().next()));
    }

    /**
     * 获取XML解析器
     * @param stylesTable
     * @param strings
     * @param sheetContentsHandler
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     */
    public static XMLReader fetchSheetParser(StylesTable stylesTable, ReadOnlySharedStringsTable strings, XSSFSheetXMLHandler.SheetContentsHandler sheetContentsHandler) throws ParserConfigurationException, SAXException {
        XMLReader parser = SAXHelper.newXMLReader();
        ContentHandler handler = new XSSFSheetXMLHandler(stylesTable, strings,
                sheetContentsHandler, false);
        parser.setContentHandler(handler);
        return parser;
    }

    /**
     * 读取excel文件, 传统方式
     * @param filename 文件名
     * @param is       输入流
     * @param handler  处理类
     */
    private static void excelRead(String filename, InputStream is, BaseSheetContentsHandler handler) throws IOException {
        try (Workbook wb = filename.endsWith("xls") ? new HSSFWorkbook(is) : new XSSFWorkbook(is)) {
            Sheet sheet = wb.getSheetAt(0);
            // 得到总行数
            int rowNum = sheet.getLastRowNum();
            logger.debug("总行数: {}", rowNum);
            // 正文内容应该从第二行开始,第一行为表头的标题
            for (int i = 0; i <= rowNum; i++) {
                Row row = sheet.getRow(i);
                int colNum = row.getPhysicalNumberOfCells();
                List<String> cellValue = new ArrayList<>(colNum);
                for (int j = 0; j < colNum; ++j) {
                    Cell cell = row.getCell(j);
                    String value = formatedCellValue(cell);
                    cellValue.add(value);
                }
                handler.onParseRow(i, cellValue);
            }
        } catch (IOException e) {
            logger.error("Excle读取失败, filename: {}", filename, e);
            throw e;
        }
    }

    /**
     * 读取单元格数据
     * @param cell 单元格
     * @return 字符串
     */
    private static String formatedCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        String cellValue;
        // 判断当前Cell的Type
        switch (cell.getCellTypeEnum()) {
            case NUMERIC:
            case FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    cellValue = sdf.format(cell.getDateCellValue());
                } else {
                    cellValue = df.format(cell.getNumericCellValue());
                }
            }
            break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case STRING:
                // 取得当前的Cell字符串
                cellValue = cell.getStringCellValue();
                break;
            default:
                // 默认的Cell值
                cellValue = "";
        }
        return cellValue;
    }
}
