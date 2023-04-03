package com.manage.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHandler extends StdDeserializer<Date> {

  public DateHandler() {
    this(null);
  }

  public DateHandler(Class<?> vc) {
    super(vc);
  }

  @Override
  public Date deserialize(JsonParser p, DeserializationContext ctxt) {
    try {
      String date = p.getText();
      if (date == null || date.isEmpty()) {
        return null;
      }
      // support different formats
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      return simpleDateFormat.parse(date);
    } catch (Exception e) {
      return null;
    }
  }
}
