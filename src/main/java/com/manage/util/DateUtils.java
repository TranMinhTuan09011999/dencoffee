package com.manage.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

  public static final String SHORT_DATE = "MM/dd/yyyy";

  public static boolean compareDate(Date dt1, Date dt2) {
    String str1 = date2String(dt1);
    String str2 = date2String(dt2);
    return str1.equals(str2);
  }

  public static String date2String(Date dt) {
    SimpleDateFormat format = new SimpleDateFormat(SHORT_DATE);
    return format.format(dt);
  }

}
