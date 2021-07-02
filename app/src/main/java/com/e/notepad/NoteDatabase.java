package com.e.notepad;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Note.class},version =  1)
@TypeConverters(DataRoomConverter.class)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao getNoteDao();
    private static String DB_NAME="database";

    private static NoteDatabase noteDB;
    public synchronized static NoteDatabase getInstance(Context context)
    {
        if(null == noteDB)
        {
            noteDB = Room.databaseBuilder(context,NoteDatabase.class,Constants.DB_NAME)
                    .allowMainThreadQueries().build();
        }

        return noteDB;
    }

    public static void cleanUp()
    {
        noteDB = null;
    }
}
