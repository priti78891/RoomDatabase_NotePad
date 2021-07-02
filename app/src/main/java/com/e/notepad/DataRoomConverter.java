package com.e.notepad;

import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import java.util.Date;

public class DataRoomConverter {
    @TypeConverter
    public static Date toDate(Long value)
    {
        return value==null?null:new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date value)
    {
        return value==null?null: value.getTime();
    }
}
