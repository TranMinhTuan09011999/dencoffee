package com.manage.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class FormatDateTimeSerializer extends StdSerializer<Date> {
  /**
   *
   */
  private static final long serialVersionUID = 1L;

  protected FormatDateTimeSerializer() {
    this(null);
  }

  protected FormatDateTimeSerializer(Class<Date> t) {
    super(t);
  }

  @Override
  public void serialize(Date date, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

    jsonGenerator.writeString(dateFormat.format(date));
  }
}
