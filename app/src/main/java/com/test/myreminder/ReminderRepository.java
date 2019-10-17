package com.test.myreminder;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ReminderRepository
{
    private ReminderDao reminderDao;
    private LiveData<List<Reminder>> allReminders;

    public ReminderRepository(Application application)
    {
        ReminderDatabase database = ReminderDatabase.getInstance(application);
        reminderDao = database.reminderDao();
        allReminders = reminderDao.getAllReminders();
    }

    public void insert(Reminder reminder)
    {
        new InsertNoteAsyncTask(reminderDao).execute(reminder);
    }

    public void update(Reminder reminder)
    {
        new UpdateNoteAsyncTask(reminderDao).execute(reminder);
    }

    public void delete(Reminder reminder)
    {
        new DeleteNoteAsyncTask(reminderDao).execute(reminder);
    }

    public void deleteAllReminders()
    {
        new DeleteAllNotesAsyncTask(reminderDao).execute();
    }

    public LiveData<List<Reminder>> getAllReminders()
    {
        return allReminders;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Reminder, Void, Void>
    {
        private ReminderDao reminderDao;
        private InsertNoteAsyncTask(ReminderDao reminderDao)
        {
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders)
        {
            reminderDao.insert(reminders[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<Reminder, Void, Void>
    {
        private ReminderDao reminderDao;
        private UpdateNoteAsyncTask(ReminderDao reminderDao)
        {
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders)
        {
            reminderDao.update(reminders[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Reminder, Void, Void>
    {
        private ReminderDao reminderDao;
        private DeleteNoteAsyncTask(ReminderDao reminderDao)
        {
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Reminder... reminders)
        {
            reminderDao.delete(reminders[0]);
            return null;
        }
    }
    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void>
    {
        private ReminderDao reminderDao;
        private DeleteAllNotesAsyncTask(ReminderDao reminderDao)
        {
            this.reminderDao = reminderDao;
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            reminderDao.deleteAllNodes();
            return null;
        }
    }
}

