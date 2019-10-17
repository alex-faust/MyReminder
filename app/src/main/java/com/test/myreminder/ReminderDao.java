package com.test.myreminder;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReminderDao
{
    //daos can only be interfaces or abstract classes
    //we dont provide body, just methods and room will generate the code
    //general rule, 1 dao per entity

    @Insert
    void insert(Reminder reminder);
    @Update
    void update(Reminder reminder);
    @Delete
    void delete(Reminder reminder);

    @Query("DELETE FROM reminder_table")
    void deleteAllNodes();

    @Query("SELECT * FROM reminder_table ORDER BY priority DESC")
    LiveData<List<Reminder>> getAllReminders();

}
