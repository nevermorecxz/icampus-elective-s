package com.irengine.campus.web.rest.util;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;

import com.irengine.campus.web.rest.dto.ResultsDTOByClass;
import com.irengine.campus.web.rest.dto.ResultsDTOByCourses;

public class CreateExcel {
	public static void createExcelByClass(ResultsDTOByClass result, HttpServletResponse response) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("预选结果");
		sheet.setColumnWidth(0, 6000);
		sheet.setColumnWidth(1, 6000);
		sheet.setColumnWidth(2, 4000);
		sheet.setColumnWidth(3, 7000);
		/* 默认单元格样式 */
		HSSFCellStyle normalStyle = workbook.createCellStyle();
		normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		normalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		/* 字体 */
		HSSFFont normalFont = workbook.createFont();
		normalFont.setFontHeightInPoints((short) 10);
		normalFont.setFontName("宋体");
		normalStyle.setFont(normalFont);
		headerStyle.setFont(normalFont);
		/* 表格内容 */
		// row1
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 400);
		// sheet.addMergedRegion(new CellRangeAddress(int firstRow, int lastRow,
		// int firstCol, int lastCol)
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		HSSFCell cel1_1 = row.createCell(0);
		cel1_1.setCellValue(result.getTitle());
		cel1_1.setCellStyle(headerStyle);
		// row2
		HSSFRow row2 = sheet.createRow(1);
		row2.setHeight((short) 400);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 3));
		HSSFCell ce2_3 = row.createCell(2);
		ce2_3.setCellStyle(headerStyle);
		ce2_3.setCellValue(result.getTeacherInfo());
		// 内容
		for (int i = 0; i < result.getStudents().size(); i++) {
			HSSFRow row_n = sheet.createRow(i + 2);
			row_n.setHeight((short) 400);
			HSSFCell ce_n_1 = row_n.createCell(0);
			ce_n_1.setCellStyle(normalStyle);
			ce_n_1.setCellValue(result.getStudents().get(i).getClassInfo());
			HSSFCell ce_n_2 = row_n.createCell(1);
			ce_n_2.setCellStyle(normalStyle);
			ce_n_2.setCellValue(result.getStudents().get(i).getStudentNum());
			HSSFCell ce_n_3 = row_n.createCell(2);
			ce_n_3.setCellStyle(normalStyle);
			ce_n_3.setCellValue(result.getStudents().get(i).getName());
			HSSFCell ce_n_4 = row_n.createCell(3);
			ce_n_4.setCellStyle(normalStyle);
			ce_n_4.setCellValue(result.getStudents().get(i).getSelectInfo());
		}
		downloadExcel(result.getTitle(), workbook, response);
	}

	public static void createExcelByCourses(ResultsDTOByCourses result, HttpServletResponse response)
			throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("预选结果");
		for (int x = 0; x < result.getData().get(0).getData().size(); x++) {
			sheet.setColumnWidth(x, 4000);
		}
		/* 默认单元格样式 */
		HSSFCellStyle normalStyle = workbook.createCellStyle();
		normalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		normalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		/* 字体 */
		HSSFFont normalfont = workbook.createFont();
		normalfont.setFontHeightInPoints((short) 10);
		normalfont.setFontName("宋体");
		normalStyle.setFont(normalfont);
		headerStyle.setFont(normalfont);
		/* 表格内容 */
		// row1
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 400);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, result.getData().get(0).getData().size() - 1));
		HSSFCell ce1_1 =row.createCell(0);
		ce1_1.setCellValue(result.getTitle());
		ce1_1.setCellStyle(headerStyle);
		// 主要内容
		for (int y = 0; y < result.getData().size(); y++) {
			HSSFRow row_y = sheet.createRow(y + 1);
			if (y == 0) {
				row_y.setHeight((short) 1000);
			} else {
				row_y.setHeight((short) 400);
			}
			for (int x = 0; x < result.getData().get(0).getData().size(); x++) {
				HSSFCell ce_y_x = row_y.createCell(x);
				if (y == 0) {
					String str = result.getData().get(y).getData().get(x).replaceAll(",", "\n");
					ce_y_x.setCellValue(str);
				} else {
					ce_y_x.setCellValue(result.getData().get(y).getData().get(x));
				}
				ce_y_x.setCellStyle(normalStyle);
			}
		}
		downloadExcel(result.getTitle(), workbook, response);
		
	}

	public static void createExcelByCourse(ResultsDTOByCourses result, HttpServletResponse response)
			throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("预选结果");
		sheet.setColumnWidth(0, 4000);
		/* 默认单元格样式 */
		HSSFCellStyle normalStyle = workbook.createCellStyle();
		normalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		normalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		normalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		/* 字体 */
		HSSFFont normalfont = workbook.createFont();
		normalfont.setFontHeightInPoints((short) 10);
		normalfont.setFontName("宋体");
		normalStyle.setFont(normalfont);
		headerStyle.setFont(normalfont);
		/* 表格内容 */
		// 第一行内容
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 400);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, result.getData().get(0).getData().size() - 1));
		HSSFCell ce1_1 = row.createCell(0);
		ce1_1.setCellValue(result.getTitle());
		ce1_1.setCellStyle(headerStyle);
		// 主要内容
		for (int y = 0; y < result.getData().size(); y++) {
			HSSFRow row_y = sheet.createRow(y + 1);
			row_y.setHeight((short) 400);
			for (int x = 0; x < result.getData().get(0).getData().size(); x++) {
				HSSFCell ce_y_x = row_y.createCell(x);
				if (y == 0) {
					String str = result.getData().get(y).getData().get(x);
					ce_y_x.setCellValue(str);
				} else {
					ce_y_x.setCellValue(result.getData().get(y).getData().get(x));
				}
				ce_y_x.setCellStyle(normalStyle);
			}
		}
		downloadExcel(result.getTitle(), workbook, response);
	}

	private static void downloadExcel(String title, HSSFWorkbook workbook, HttpServletResponse response)
			throws IOException {
		OutputStream out = response.getOutputStream();
		response.reset();
		response.setContentType("application/x-msdownload");
		String pName = title;
		response.setHeader("Content-Disposition",
				"attachment; filename=" + new String(pName.getBytes("gb2312"), "ISO-8859-1") + ".xls");
		try {
			workbook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}
}