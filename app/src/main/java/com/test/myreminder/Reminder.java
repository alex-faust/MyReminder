package com.test.myreminder;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "reminder_table")
class Reminder
{
    @PrimaryKey(autoGenerate = true)
    private int id;
    //@Ignore the field and it will not be added to the table.
    private String name;
    private String description;
    //@ColumnInfo(name = "priority_column") can change the name of the column
    private int priority;
    private String date;
    private String time;

    public Reminder(String name, String description, int priority, String date, String time)
    {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.date = date;
        this.time = time;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public int getPriority()
    {
        return priority;
    }

    public String getDate()
    {
        return date;
    }

    public String getTime()
    {
        return time;
    }
}
