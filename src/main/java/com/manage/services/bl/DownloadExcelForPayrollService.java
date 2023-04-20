package com.manage.services.bl;

import com.manage.dto.AttendanceDTO;
import com.manage.dto.AttendanceDetailsForEmployeeDTO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.SystemException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class DownloadExcelForPayrollService {

  private static final Logger logger = LoggerFactory.getLogger(DownloadExcelForPayrollService.class);

  @Autowired
  private GetPayrollForMonthYearService getPayrollForMonthYearService;

  public boolean downloadExcelForPayroll(Integer month, Integer year) throws SystemException {
    try {
      return createExcelFile(month, year);
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  private boolean createExcelFile(Integer month, Integer year) {
    try {
      String filePath = "D:\\denCoffeeSalary.xlsx";
      File excelFile = new File(filePath);
      Workbook workbook;
      if (excelFile.exists()) {
        workbook = new XSSFWorkbook(excelFile);
        System.out.println("Excel file exists at " + filePath);
      } else {
        workbook = new XSSFWorkbook();
      }
      String sheetNameFormat = "%d-%d";
      int sheetName = workbook.getSheetIndex(String.format(sheetNameFormat, month, year));
      if (sheetName != -1) {
        return false;
      }
      Sheet sheet = workbook.createSheet(String.format(sheetNameFormat, month, year));

      setTitle(workbook, sheet, month, year);
      setHeader(workbook, sheet, month, year);
      setData(workbook, sheet, month, year);

      FileOutputStream outputStream = new FileOutputStream(filePath);
      workbook.write(outputStream);
      workbook.close();
    } catch (IOException | InvalidFormatException | SystemException e) {
      e.printStackTrace();
    }
    return true;
  }

  private void setTitle(Workbook workbook, Sheet sheet, Integer month, Integer year) {
    String sheetNameFormat = "%d-%d";
    Row row = sheet.createRow(3);
    Cell cell = row.createCell(12);
    cell.setCellValue("BẢNG LƯƠNG THÁNG " + String.format(sheetNameFormat, month, year));

    CellStyle cellStyle = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setColor(IndexedColors.RED.getIndex());
    font.setBold(true);
    cellStyle.setFont(font);
    cell.setCellStyle(cellStyle);;
  }

  private void setHeader(Workbook workbook, Sheet sheet, Integer month, Integer year) {
    LocalDate date = LocalDate.of(year, month, 1);
    int daysInMonth = date.lengthOfMonth();
    Row row = sheet.createRow(6);
    for (int i=0; i<daysInMonth; i++) {
      Cell cell = row.createCell(4 + i);
      cell.setCellValue(i + 1);
      CellStyle styleForCell = workbook.createCellStyle();
      styleForCell.setAlignment(HorizontalAlignment.CENTER);
      styleForCell.setBorderTop(BorderStyle.THIN);
      styleForCell.setBorderLeft(BorderStyle.THIN);
      styleForCell.setBorderRight(BorderStyle.THIN);
      styleForCell.setBorderBottom(BorderStyle.THIN);
      styleForCell.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
      styleForCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
      cell.setCellStyle(styleForCell);
      sheet.setColumnWidth(4 + i, 4 * 256);
    }

    Cell cellForHourTotal = row.createCell(4 + daysInMonth);
    cellForHourTotal.setCellValue("TỔNG GIỜ");
    Cell cellForBasicSalary = row.createCell(4 + daysInMonth + 1);
    cellForBasicSalary.setCellValue("LƯƠNG CƠ BẢN");
    Cell cellForSalaryTotal = row.createCell(4 + daysInMonth + 2);
    cellForSalaryTotal.setCellValue("TỔNG LƯƠNG");
    CellStyle styleForCell = workbook.createCellStyle();
    styleForCell.setAlignment(HorizontalAlignment.CENTER);
    styleForCell.setBorderTop(BorderStyle.THIN);
    styleForCell.setBorderLeft(BorderStyle.THIN);
    styleForCell.setBorderRight(BorderStyle.THIN);
    styleForCell.setBorderBottom(BorderStyle.THIN);
    styleForCell.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
    styleForCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    cellForHourTotal.setCellStyle(styleForCell);
    cellForBasicSalary.setCellStyle(styleForCell);
    cellForSalaryTotal.setCellStyle(styleForCell);
    sheet.setColumnWidth(4 + daysInMonth, 14 * 256);
    sheet.setColumnWidth(4 + daysInMonth + 1, 18 * 256);
    sheet.setColumnWidth(4 + daysInMonth + 2, 17 * 256);

    CellRangeAddress mergedCellForMonths = new CellRangeAddress(5, 5, 4, 3 + daysInMonth);
    sheet.addMergedRegion(mergedCellForMonths);
    RegionUtil.setBorderTop(BorderStyle.THIN, mergedCellForMonths, sheet);
    RegionUtil.setBorderBottom(BorderStyle.THIN, mergedCellForMonths, sheet);
    RegionUtil.setBorderLeft(BorderStyle.THIN, mergedCellForMonths, sheet);
    RegionUtil.setBorderRight(BorderStyle.THIN, mergedCellForMonths, sheet);

    XSSFCell mergedCellForMonth = (XSSFCell) sheet.getRow(5).getCell(4);
    String sheetNameFormat = "%d-%d";
    mergedCellForMonth.setCellValue("NGÀY TRONG THÁNG " + String.format(sheetNameFormat, month, year));
    CellStyle cellStyle = workbook.createCellStyle();
    cellStyle.setAlignment(HorizontalAlignment.CENTER);
    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
    cellStyle.setBorderTop(BorderStyle.THIN);
    cellStyle.setBorderLeft(BorderStyle.THIN);
    cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
    cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    mergedCellForMonth.setCellStyle(cellStyle);

    CellRangeAddress mergedCellForNames = new CellRangeAddress(5, 6, 1, 3);
    sheet.addMergedRegion(mergedCellForNames);
    RegionUtil.setBorderTop(BorderStyle.THIN, mergedCellForNames, sheet);
    RegionUtil.setBorderBottom(BorderStyle.THIN, mergedCellForNames, sheet);
    RegionUtil.setBorderLeft(BorderStyle.THIN, mergedCellForNames, sheet);
    RegionUtil.setBorderRight(BorderStyle.THIN, mergedCellForNames, sheet);

    XSSFCell mergedCellForName = (XSSFCell) sheet.getRow(5).getCell(1);
    mergedCellForName.setCellValue("HỌ VÀ TÊN");
    CellStyle cellStyleForName = workbook.createCellStyle();
    cellStyleForName.setAlignment(HorizontalAlignment.CENTER);
    cellStyleForName.setVerticalAlignment(VerticalAlignment.CENTER);
    cellStyleForName.setBorderTop(BorderStyle.THIN);
    cellStyleForName.setBorderLeft(BorderStyle.THIN);
    cellStyleForName.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
    cellStyleForName.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    mergedCellForName.setCellStyle(cellStyleForName);
  }

  private void setData(Workbook workbook, Sheet sheet, Integer month, Integer year) throws SystemException {
    LocalDate date = LocalDate.of(year, month, 1);
    int daysInMonth = date.lengthOfMonth();
    List<AttendanceDetailsForEmployeeDTO> attendanceDetailsForEmployeeDTOList =
            getPayrollForMonthYearService.getPayrollForMonthYear(month, year);
    for (int i = 0; i < attendanceDetailsForEmployeeDTOList.size(); i++) {
      Row row = sheet.createRow(7 + i);
      CellRangeAddress mergedCellForNames = new CellRangeAddress(7 + i, 7 + i, 1, 3);
      sheet.addMergedRegion(mergedCellForNames);
      RegionUtil.setBorderTop(BorderStyle.THIN, mergedCellForNames, sheet);
      RegionUtil.setBorderBottom(BorderStyle.THIN, mergedCellForNames, sheet);
      RegionUtil.setBorderLeft(BorderStyle.THIN, mergedCellForNames, sheet);
      RegionUtil.setBorderRight(BorderStyle.THIN, mergedCellForNames, sheet);
      XSSFCell mergedCellForName = (XSSFCell) sheet.getRow(7 + i).getCell(1);
      mergedCellForName.setCellValue(attendanceDetailsForEmployeeDTOList.get(i).getFullname());
      double hourTotalOfMonth = 0;
      for (int j=0; j<daysInMonth; j++) {
        double hourTotalOfDay = getHourTotalOfDay(j+1, attendanceDetailsForEmployeeDTOList.get(i).getAttendanceDTOList());
        hourTotalOfMonth += hourTotalOfDay;
        Cell cell = row.createCell(4+j);
        cell.setCellValue(hourTotalOfDay);
        CellStyle styleForCell = workbook.createCellStyle();
        styleForCell.setAlignment(HorizontalAlignment.CENTER);
        styleForCell.setBorderRight(BorderStyle.THIN);
        styleForCell.setBorderBottom(BorderStyle.THIN);
        cell.setCellStyle(styleForCell);
      }
      Cell cell = row.createCell(4 + daysInMonth + 0);
      cell.setCellValue(hourTotalOfMonth);
      CellStyle styleForCell = workbook.createCellStyle();
      styleForCell.setAlignment(HorizontalAlignment.CENTER);
      styleForCell.setBorderRight(BorderStyle.THIN);
      styleForCell.setBorderBottom(BorderStyle.THIN);
      cell.setCellStyle(styleForCell);

      Cell cell1 = row.createCell(4 + daysInMonth + 1);
      DecimalFormat df = new DecimalFormat("#,###");
      cell1.setCellValue(df.format(attendanceDetailsForEmployeeDTOList.get(i).getCurrentSalary()));
      CellStyle styleForCell1 = workbook.createCellStyle();
      styleForCell1.setAlignment(HorizontalAlignment.CENTER);
      styleForCell1.setAlignment(HorizontalAlignment.CENTER);
      styleForCell1.setBorderRight(BorderStyle.THIN);
      styleForCell1.setBorderBottom(BorderStyle.THIN);
      cell1.setCellStyle(styleForCell1);

      Cell cell2 = row.createCell(4 + daysInMonth + 2);
      cell2.setCellValue(getSalary(hourTotalOfMonth, attendanceDetailsForEmployeeDTOList.get(i).getCurrentSalary()));
      CellStyle styleForCell2 = workbook.createCellStyle();
      styleForCell2.setAlignment(HorizontalAlignment.CENTER);
      styleForCell2.setBorderRight(BorderStyle.THIN);
      styleForCell2.setBorderBottom(BorderStyle.THIN);
      cell2.setCellStyle(styleForCell2);
    }
  }

  private double getHourTotalOfDay(Integer day, List<AttendanceDTO> attendanceDTOList) {
    if (Objects.nonNull(attendanceDTOList) && !attendanceDTOList.isEmpty()) {
      double hourTotal = attendanceDTOList.stream().filter(item -> item.getStartDateTime().getDay() == day)
              .mapToDouble(item -> getHourTotalBBetween2Time(item.getStartDateTime(), item.getEndDateTime())).sum();
      return hourTotal;
    }
    return 0;
  }

  private double getHourTotalBBetween2Time(Date startDateTime, Date endDateTime) {
    if (endDateTime != null) {
      var diff = endDateTime.getTime() - startDateTime.getTime();
      var hours = diff / (1000 * 60 * 60);
      var integerPart = Math.floor(hours);
      var decimalPart = (hours - integerPart) * 60;
      if (decimalPart >= 25 && decimalPart <= 55) {
        return integerPart + 0.5;
      } else if (decimalPart > 55) {
        return integerPart + 1;
      }
      return integerPart;
    }
    return 0;
  }

  private String getSalary(double hourTotal, Double basicSalary) {
    Double salaryTotal = hourTotal * basicSalary;
    DecimalFormat df = new DecimalFormat("#,###");
    return df.format(salaryTotal);
  }
}