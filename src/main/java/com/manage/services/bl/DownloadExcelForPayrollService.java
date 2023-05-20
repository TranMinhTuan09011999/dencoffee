package com.manage.services.bl;

import com.manage.dto.AttendanceDTO;
import com.manage.dto.EmployeePayrollDTO;
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
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DownloadExcelForPayrollService {

  private static final Logger logger = LoggerFactory.getLogger(DownloadExcelForPayrollService.class);

  @Autowired
  private GetPayrollForMonthYearService getPayrollForMonthYearService;

  public Map<String, Object> downloadExcelForPayroll(Integer month, Integer year) throws SystemException {
    try {
      return createExcelFile(month, year);
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
  }

  public Map<String, Object> downloadAllMonthForPayroll() throws SystemException {
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      LocalDate currentDate = LocalDate.now();
      String excelFileName = "Bảng_Lương_Nhân_Viên";
      Workbook workbook = new XSSFWorkbook();
      for (int i = 12; i >= 1; i--) {
        LocalDate date = currentDate.minusMonths(i).withDayOfMonth(1);
        createExcelFileForAll(workbook, date);
      }
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      workbook.write(outputStream);
      workbook.close();
      result.put("data", outputStream.toByteArray());
      result.put("fileName", URLEncoder.encode(excelFileName, "UTF-8"));
    } catch (Exception e) {
      logger.error("Error", e);
      throw new SystemException();
    }
    return result;
  }

  private void createExcelFileForAll(Workbook workbook, LocalDate date) {
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      String monthYearFormat = date.format(DateTimeFormatter.ofPattern("MM-yyyy"));
      Sheet sheet = workbook.createSheet(String.format(monthYearFormat));

      Integer month = date.getMonthValue();
      Integer year = date.getYear();

      setTitle(workbook, sheet, month, year);
      setHeader(workbook, sheet, month, year);
      setData(workbook, sheet, month, year);
    } catch (SystemException e) {
      e.printStackTrace();
    }
  }

  private Map<String, Object> createExcelFile(Integer month, Integer year) {
    Map<String, Object> result = new HashMap<String, Object>();
    String excelFileName = null;
    try {
      String sheetNameFormat = "Lương_Tháng_%d-%d";
      excelFileName = String.format(sheetNameFormat, month, year);
      Workbook workbook = new XSSFWorkbook();

      Sheet sheet = workbook.createSheet(String.format(sheetNameFormat, month, year));

      setTitle(workbook, sheet, month, year);
      setHeader(workbook, sheet, month, year);
      setData(workbook, sheet, month, year);

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      workbook.write(outputStream);
      workbook.close();

      result.put("data", outputStream.toByteArray());
      result.put("fileName", URLEncoder.encode(excelFileName, "UTF-8"));
    } catch (IOException | SystemException e) {
      e.printStackTrace();
    }
    return result;
  }

  private void setTitle(Workbook workbook, Sheet sheet, Integer month, Integer year) {
    String sheetNameFormat = "%d-%d";
    Row row = sheet.createRow(3);
    CellStyle cellStyle = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setColor(IndexedColors.RED.getIndex());
    font.setBold(true);
    cellStyle.setFont(font);
    createCell(row, sheet, 12, "BẢNG LƯƠNG THÁNG " + String.format(sheetNameFormat, month, year), cellStyle, 0);
  }

  private void setHeader(Workbook workbook, Sheet sheet, Integer month, Integer year) {
    LocalDate date = LocalDate.of(year, month, 1);
    int daysInMonth = date.lengthOfMonth();
    Row row = sheet.createRow(6);
    Calendar calendar = Calendar.getInstance();
    calendar.set(year, month - 1, 1);
    while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
      calendar.add(Calendar.DAY_OF_MONTH, 1);
    }
    List<Integer> sundayDayList = new ArrayList<>();
    while (calendar.get(Calendar.MONTH) == month - 1) {
      int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
      sundayDayList.add(dayOfMonth);
      calendar.add(Calendar.DAY_OF_MONTH, 7);
    }
    for (int i=0; i<daysInMonth; i++) {
      IndexedColors color = null;
      if (sundayDayList.contains(i+1)) {
        color = IndexedColors.RED;
      } else {
        color = IndexedColors.PALE_BLUE;
      }
      CellStyle cellStyle = setCssForCell(workbook, HorizontalAlignment.CENTER, BorderStyle.THIN, BorderStyle.THIN,
              BorderStyle.THIN, BorderStyle.THIN, color, FillPatternType.SOLID_FOREGROUND, null);
      createCell(row, sheet, 4 + i, String.valueOf(i + 1), cellStyle, 4);
    }
    CellStyle styleForCell = setCssForCell(workbook, HorizontalAlignment.CENTER, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN,
            IndexedColors.PALE_BLUE, FillPatternType.SOLID_FOREGROUND, null);
    createCell(row, sheet, 4 + daysInMonth, "TỔNG GIỜ", styleForCell, 12);
    createCell(row, sheet, 4 + daysInMonth + 1, "LƯƠNG CƠ BẢN", styleForCell, 15);
    createCell(row, sheet, 4 + daysInMonth + 2, "TRỢ CẤP", styleForCell, 15);
    createCell(row, sheet, 4 + daysInMonth + 3, "THƯỞNG", styleForCell, 15);
    createCell(row, sheet, 4 + daysInMonth + 4, "THƯỞNG (CN)", styleForCell, 15);
    createCell(row, sheet, 4 + daysInMonth + 5, "ỨNG TRƯỚC", styleForCell, 15);
    createCell(row, sheet, 4 + daysInMonth + 6, "LƯƠNG", styleForCell, 15);
    createCell(row, sheet, 4 + daysInMonth + 7, "THỰC LÃNH", styleForCell, 15);
    createCell(row, sheet, 4 + daysInMonth + 8, "THANH TOÁN LƯƠNG", styleForCell, 19);

    String sheetNameFormat = "%d-%d";
    String value = "NGÀY TRONG THÁNG " + String.format(sheetNameFormat, month, year);
    CellStyle cellStyle = setCssForCell(workbook, HorizontalAlignment.CENTER, null, null, BorderStyle.THIN, BorderStyle.THIN, IndexedColors.PALE_BLUE,
            FillPatternType.SOLID_FOREGROUND, null);
    mergeCell(sheet, 5, 5, 4, 3 + daysInMonth, value, cellStyle);

    CellStyle cellStyleForName = setCssForCell(workbook, HorizontalAlignment.CENTER, null, null, BorderStyle.THIN, BorderStyle.THIN,
            IndexedColors.PALE_BLUE, FillPatternType.SOLID_FOREGROUND, VerticalAlignment.CENTER);
    mergeCell(sheet, 5, 6, 1, 3, "HỌ VÀ TÊN", cellStyleForName);
  }

  private void setData(Workbook workbook, Sheet sheet, Integer month, Integer year) throws SystemException {
    LocalDate date = LocalDate.of(year, month, 1);
    int daysInMonth = date.lengthOfMonth();
    List<EmployeePayrollDTO> attendanceDetailsForEmployeeDTOList =
            getPayrollForMonthYearService.getPayrollForMonthYear(month, year);
    for (int i = 0; i < attendanceDetailsForEmployeeDTOList.size(); i++) {
      Row row = sheet.createRow(7 + i);
      IndexedColors color = null;
      if (attendanceDetailsForEmployeeDTOList.get(i).getPosition().equals("Phục vụ")) {
        color = IndexedColors.ORANGE;
      } else if (attendanceDetailsForEmployeeDTOList.get(i).getPosition().equals("Pha chế")) {
        color = IndexedColors.YELLOW;
      } else if (attendanceDetailsForEmployeeDTOList.get(i).getPosition().equals("Quản lý")) {
        color = IndexedColors.LIGHT_GREEN;
      }
      CellStyle cellStyleForName = setCssForCell(workbook, HorizontalAlignment.CENTER, null, BorderStyle.THIN, null, BorderStyle.THIN,
              color, FillPatternType.SOLID_FOREGROUND, null);
      mergeCell(sheet, 7 + i, 7 + i, 1, 3, attendanceDetailsForEmployeeDTOList.get(i).getFullname(), cellStyleForName);
      CellStyle styleForCell = setCssForCell(workbook, HorizontalAlignment.CENTER, BorderStyle.THIN, BorderStyle.THIN, null, null, null, null, null);
      for (int j = 0; j < daysInMonth; j++) {
        double hourTotalOfDay = 0.0;
        List<AttendanceDTO> attendanceDTOList = getAttendanceForDay(j+1, attendanceDetailsForEmployeeDTOList.get(i).getAttendanceDTOList());
        if (attendanceDTOList != null && attendanceDTOList.size() > 0) {
          hourTotalOfDay = getHourTotalOfDay(attendanceDTOList);
        }
        IndexedColors colorForDay = null;
        if (checkDelay(attendanceDTOList)) {
          colorForDay = IndexedColors.GREY_25_PERCENT;
        }
        CellStyle styleForCellForDay = setCssForCell(workbook, HorizontalAlignment.CENTER, BorderStyle.THIN, BorderStyle.THIN, null, null, colorForDay, FillPatternType.SOLID_FOREGROUND, null);
        createCell(row, sheet, 4+j, getDoubleValue(hourTotalOfDay), styleForCellForDay, 0);
      }
      createCell(row, sheet, 4 + daysInMonth, getDoubleValue(attendanceDetailsForEmployeeDTOList.get(i).getHourTotal()), styleForCell, 12);
      createCell(row, sheet, 4 + daysInMonth + 1, addCommaForSalary(attendanceDetailsForEmployeeDTOList.get(i).getHourSalary()), styleForCell, 15);
      createCell(row, sheet, 4 + daysInMonth + 2, addCommaForSalary(attendanceDetailsForEmployeeDTOList.get(i).getAllowance()), styleForCell, 15);
      createCell(row, sheet, 4 + daysInMonth + 3, addCommaForSalary(attendanceDetailsForEmployeeDTOList.get(i).getBonus()), styleForCell, 15);
      createCell(row, sheet, 4 + daysInMonth + 4, addCommaForSalary(attendanceDetailsForEmployeeDTOList.get(i).getSundayBonus()), styleForCell, 15);
      createCell(row, sheet, 4 + daysInMonth + 5, addCommaForSalary(attendanceDetailsForEmployeeDTOList.get(i).getSalaryAdvance()), styleForCell, 15);
      createCell(row, sheet, 4 + daysInMonth + 6, addCommaForSalary(attendanceDetailsForEmployeeDTOList.get(i).getSalaryAmount()), styleForCell, 15);
      createCell(row, sheet, 4 + daysInMonth + 7, addCommaForSalary(attendanceDetailsForEmployeeDTOList.get(i).getActualSalary()), styleForCell, 15);
      if (attendanceDetailsForEmployeeDTOList.get(i).getPaymentStatus() == true) {
        createCell(row, sheet, 4 + daysInMonth + 8, "ĐÃ THANH TOÁN", styleForCell, 19);
      } else {
        createCell(row, sheet, 4 + daysInMonth + 8, "CHƯA THANH TOÁN", styleForCell, 19);
      }
    }
    Row row = sheet.createRow(7 + attendanceDetailsForEmployeeDTOList.size());
    CellStyle styleForCell = null;
    for (int i = 0; i < daysInMonth; i++) {
      BorderStyle borderLeft = null;
      if (i == 0) {
        borderLeft = BorderStyle.THIN;
      }
      styleForCell = setCssForCell(workbook, HorizontalAlignment.CENTER, BorderStyle.THIN, BorderStyle.THIN, null, borderLeft, null, null, null);
      double total = 0.0;
      for (int j = 0; j < attendanceDetailsForEmployeeDTOList.size(); j++) {
        List<AttendanceDTO> attendanceDTOList = getAttendanceForDay(i+1, attendanceDetailsForEmployeeDTOList.get(j).getAttendanceDTOList());
        total += getHourTotalOfDay(attendanceDTOList);
      }
      createCell(row, sheet, 4+i, getDoubleValue(total), styleForCell, 0);
    }

    setNote(workbook, sheet, attendanceDetailsForEmployeeDTOList.size() + 9);
  }

  private double getHourTotalOfDay(List<AttendanceDTO> attendanceDTOList) {
    double result = 0.0;
    if (Objects.nonNull(attendanceDTOList) && !attendanceDTOList.isEmpty()) {
      for (int i=0; i<attendanceDTOList.size(); i++) {
        result += getHourTotalBetween2Time(attendanceDTOList.get(i).getStartDateTime(), attendanceDTOList.get(i).getEndDateTime());
      }
    }
    return result;
  }

  private double getHourTotalBetween2Time(Date startDateTime, Date endDateTime) {
    if (endDateTime != null) {
      long diff = endDateTime.getTime() - startDateTime.getTime();
      double hours = (double) diff / (1000*60*60);
      double integerPart = Math.floor(hours);
      var decimalPart = (hours - integerPart) * 60;
      if (decimalPart >= 25 && decimalPart < 55) {
        return integerPart + 0.5;
      } else if (decimalPart >= 55) {
        return integerPart + 1;
      }
      return integerPart;
    }
    return 0;
  }

  private void createCell(Row row, Sheet sheet, int column, String value, CellStyle styleForCell, int width) {
    Cell cell = row.createCell(column);
    if (value != null) {
      cell.setCellValue(value);
    }
    if (styleForCell != null) {
      cell.setCellStyle(styleForCell);
    }
    if (width > 0) {
      sheet.setColumnWidth(column, width * 256);
    }
  }

  private void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol, String value, CellStyle cellStyle) {
    CellRangeAddress mergedCell = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
    sheet.addMergedRegion(mergedCell);
    RegionUtil.setBorderTop(BorderStyle.THIN, mergedCell, sheet);
    RegionUtil.setBorderBottom(BorderStyle.THIN, mergedCell, sheet);
    RegionUtil.setBorderLeft(BorderStyle.THIN, mergedCell, sheet);
    RegionUtil.setBorderRight(BorderStyle.THIN, mergedCell, sheet);

    XSSFCell cell = (XSSFCell) sheet.getRow(firstRow).getCell(firstCol);
    cell.setCellValue(value);
    cell.setCellStyle(cellStyle);
  }

  private CellStyle setCssForCell(Workbook workbook, HorizontalAlignment alignment,
                             BorderStyle borderRight, BorderStyle borderBottom,
                             BorderStyle borderTop, BorderStyle borderLeft, IndexedColors fillForegroundColor,
                             FillPatternType fillPattern, VerticalAlignment verticalAlignment) {
    CellStyle styleForCell = workbook.createCellStyle();
    if (alignment != null) {
      styleForCell.setAlignment(alignment);
    }
    if (borderRight != null) {
      styleForCell.setBorderRight(borderRight);
    }
    if (borderBottom != null) {
      styleForCell.setBorderBottom(borderBottom);
    }
    if (borderTop != null) {
      styleForCell.setBorderTop(borderTop);
    }
    if (borderLeft != null) {
      styleForCell.setBorderLeft(borderLeft);
    }
    if (fillForegroundColor != null) {
      styleForCell.setFillForegroundColor(fillForegroundColor.getIndex());
    }
    if (fillPattern != null) {
      styleForCell.setFillPattern(fillPattern);
    }
    if (verticalAlignment != null) {
      styleForCell.setVerticalAlignment(verticalAlignment);
    }
    return styleForCell;
  }

  private String getDoubleValue(double number) {
    int integerPart = (int) number;
    double decimalPart = number - integerPart;
    if (decimalPart > 0) {
      return String.valueOf(number);
    }
    return String.valueOf(integerPart);
  }

  private String addCommaForSalary(double value) {
    double number = Double.valueOf(getDoubleValue(value));
    DecimalFormat df = new DecimalFormat("#,###");
    return df.format(number);
  }

  private void setNote(Workbook workbook, Sheet sheet, int rownum) {
    Row row = sheet.createRow(rownum);
    createCell(row, sheet, 1, "CHÚ THÍCH", null, 0);
    CellStyle cellStyle = setCssForCell(workbook, HorizontalAlignment.CENTER, BorderStyle.THIN, BorderStyle.THIN,
            BorderStyle.THIN, BorderStyle.THIN, null, FillPatternType.SOLID_FOREGROUND, null);
    Row row1 = sheet.createRow(rownum + 2);
    createCell(row1, sheet, 1, "VỊ TRÍ", cellStyle, 0);
    createCell(row1, sheet, 2, "MÀU", cellStyle, 0);
    Row row2 = sheet.createRow(rownum + 3);
    CellStyle cellStyle2 = setCssForCell(workbook, HorizontalAlignment.CENTER, BorderStyle.THIN, BorderStyle.THIN,
            null, BorderStyle.THIN, IndexedColors.LIGHT_GREEN, FillPatternType.SOLID_FOREGROUND, null);
    createCell(row2, sheet, 1, "QUẢN LÝ", cellStyle, 0);
    createCell(row2, sheet, 2, null, cellStyle2, 0);
    Row row3 = sheet.createRow(rownum + 4);
    CellStyle cellStyle3 = setCssForCell(workbook, HorizontalAlignment.CENTER, BorderStyle.THIN, BorderStyle.THIN,
            null, BorderStyle.THIN, IndexedColors.YELLOW, FillPatternType.SOLID_FOREGROUND, null);
    createCell(row3, sheet, 1, "PHA CHẾ", cellStyle, 0);
    createCell(row3, sheet, 2, null, cellStyle3, 0);
    Row row4 = sheet.createRow(rownum + 5);
    CellStyle cellStyle4 = setCssForCell(workbook, HorizontalAlignment.CENTER, BorderStyle.THIN, BorderStyle.THIN,
            null, BorderStyle.THIN, IndexedColors.ORANGE, FillPatternType.SOLID_FOREGROUND, null);
    createCell(row4, sheet, 1, "PHỤC VỤ", cellStyle, 0);
    createCell(row4, sheet, 2, null, cellStyle4, 0);

    Row row5 = sheet.createRow(rownum + 7);
    CellStyle cellStyle5 = setCssForCell(workbook, HorizontalAlignment.CENTER, BorderStyle.THIN, BorderStyle.THIN,
            BorderStyle.THIN, BorderStyle.THIN, IndexedColors.RED, FillPatternType.SOLID_FOREGROUND, null);
    createCell(row5, sheet, 1, "CHỦ NHẬT", cellStyle, 12);
    createCell(row5, sheet, 2, null, cellStyle5, 0);
  }

  private List<AttendanceDTO> getAttendanceForDay(Integer day, List<AttendanceDTO> attendanceDTOList) {
    if (attendanceDTOList != null && attendanceDTOList.size() > 0) {
      return attendanceDTOList.stream().filter(item -> item.getStartDateTime().getDate() == day).collect(Collectors.toList());
    }
    return null;
  }

  private boolean checkDelay(List<AttendanceDTO> attendanceDTOList) {
    if (Objects.nonNull(attendanceDTOList) && !attendanceDTOList.isEmpty()) {
      for (int i=0; i<attendanceDTOList.size(); i++) {
        if (attendanceDTOList.get(i).getActualStartDateTime().getMinutes() > 0
                && attendanceDTOList.get(i).getActualStartDateTime().getMinutes() < 10) {
          return true;
        }
      }
    }
    return false;
  }
}