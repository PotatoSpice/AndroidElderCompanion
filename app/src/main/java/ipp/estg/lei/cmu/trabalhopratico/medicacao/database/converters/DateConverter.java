package ipp.estg.lei.cmu.trabalhopratico.medicacao.database.converters;

import android.util.Log;

import androidx.room.TypeConverter;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateConverter {

    /**
     * Parses string to date with format dd/MM/yyyy
     *
     * @param value date string
     * @return java.sql.date instance
     */
    @TypeConverter
    public static Date dateString(String value) {
        if (value == null)
            return null;
        else {
            java.util.Date date;
            try {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                date = format.parse(value);
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
            return new Date(date.getTime());
        }
    }

    @TypeConverter
    public static String dateToString(Date date) {
        return date == null ? null : date.toString();
    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

}
