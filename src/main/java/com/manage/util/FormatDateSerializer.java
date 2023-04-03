package com.manage.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDateSerializer extends StdSerializer<Date> {
  /**
   *
   */
  private static final long serialVersionUID = 1L;

  protected FormatDateSerializer() {
    this(null);
  }

  protected FormatDateSerializer(Class<Date> t) {
    super(t);
  }

  @Override
  public void serialize(Date date, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    DateFormat dateFormat = new SimpleDateFormat("dd//MM/yyyy");

    jsonGenerator.writeString(dateFormat.format(date));
  }
}
