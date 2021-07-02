package com.e.notepad;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = Constants.TABLE_NAME_NOTE)
public class Note implements Serializable {
@PrimaryKey(autoGenerate = true)
    private int note_id;

@ColumnInfo(name = "note_content")//column name will be "note_content" instead of "content" in table
    private String content;
    private  String title;
    private Date date;
    public Note(int note_id,String content,String title)
    {
        this.note_id = note_id;
        this.content = content;
        this.title = title;
        this.date = new Date(System.currentTimeMillis());
    }
    @Ignore
    public Note(String content,String title) {
        this.content = content;
        this.title=title;
        this.date=new Date(System.currentTimeMillis());
    }
    @Ignore
    public Note()
    {}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object obj) {
        if(this==obj)return true;
        if (!(obj instanceof Note))return false;
        Note note = (Note) obj;

        if(note_id!=note.note_id)return false;
        return title!=null?title.equals(note.title):note.title==null;

    }

    @Override
    public int hashCode() {
        int result = (int)note_id;
        result = 3*result+(title !=null?title.hashCode():0);
        return result;

    }


    @Override
    public String toString() {
        return "Note{"+
                "note_id"+note_id+
                ",content ='"+content+'\''+
                ",title = "+title+'\''+
                '}';
    }
}
