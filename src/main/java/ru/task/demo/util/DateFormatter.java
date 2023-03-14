package ru.task.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private static final SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    public static Date toDate(String date) {
        try {
            return df.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String toStr(Date date) {
        return df.format(date);
    }
}
