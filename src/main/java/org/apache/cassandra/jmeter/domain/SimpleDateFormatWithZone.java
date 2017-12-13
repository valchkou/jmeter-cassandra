package org.apache.cassandra.jmeter.domain;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class SimpleDateFormatWithZone extends SimpleDateFormat {
    public SimpleDateFormatWithZone(String format, TimeZone zone) {
        super(format);
        this.setTimeZone(zone);
    }
}
