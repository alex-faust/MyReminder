package com.test.myreminder;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = Reminder.class, version = 2)
public abstract class ReminderDatabase extends RoomDatabase
{
    private static ReminderDatabase instance;
    //created because class needs to be singleton
    //can not create multiple instances of this class everywhere
    public abstract ReminderDao reminderDao();

    public static synchronized ReminderDatabase getInstance(Context context) {
        //in multithread environment, sometimes more than one can be created so synchronized means only one
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ReminderDatabase.class, "reminder_database")
                    .fallbackToDestructiveMigration().addCallback(roomCallBack).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback()
    {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db)
        {
            super.onCreate(db);
            new PopulateDBAsyncTask(instance).execute();
        }
    };

    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private ReminderDao reminderDao;

        private PopulateDBAsyncTask(ReminderDatabase db)
        {
            reminderDao = db.reminderDao();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            //reminderDao.insert(new Reminder("Title 1", "Description 1", 1 ));
            //reminderDao.insert(new Reminder("Title 2", "Description 2", 2 ));
            //reminderDao.insert(new Reminder("Title 3", "Description 3", 3 ));

            return null;
        }
    }
}

