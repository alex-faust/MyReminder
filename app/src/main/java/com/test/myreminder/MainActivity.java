package com.test.myreminder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity
{
    private static final int ADD_REM_REQUEST = 1;
    private static final int EDIT_REM_REQUEST = 2;

    private ReminderViewModel reminderViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.button_add_reminder);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final ReminderAdapter adapter = new ReminderAdapter();
        recyclerView.setAdapter(adapter);

        reminderViewModel = ViewModelProviders.of(this).get(ReminderViewModel.class);
        reminderViewModel.getAllReminders().observe(this, adapter::submitList);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)
        {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target)
            {
                Toast.makeText(MainActivity.this, "onMove", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction)
            {
                //insert snackbar here
                reminderViewModel.delete(adapter.getReminderAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Reminder deleted", Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(reminder -> {
            Intent intent = new Intent(MainActivity.this, AddEditReminderActivity.class);
            intent.putExtra(AddEditReminderActivity.EXTRA_ID, reminder.getId());
            intent.putExtra(AddEditReminderActivity.EXTRA_NAME, reminder.getName());
            intent.putExtra(AddEditReminderActivity.EXTRA_DESCRIPTION, reminder.getDescription());
            intent.putExtra(AddEditReminderActivity.EXTRA_PRIORITY, reminder.getPriority());
            intent.putExtra(AddEditReminderActivity.EXTRA_DATE, reminder.getDate());
            intent.putExtra(AddEditReminderActivity.EXTRA_TIME, reminder.getTime());
            startActivityForResult(intent, EDIT_REM_REQUEST);
        });

        fab.setOnClickListener( v ->
        {
            Intent intent = new Intent(MainActivity.this, AddEditReminderActivity.class);
            startActivityForResult(intent, ADD_REM_REQUEST);

        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_REM_REQUEST && resultCode == RESULT_OK)
        {
            String name = data.getStringExtra(AddEditReminderActivity.EXTRA_NAME);
            String description = data.getStringExtra(AddEditReminderActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditReminderActivity.EXTRA_PRIORITY, 1);
            String date = data.getStringExtra(AddEditReminderActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddEditReminderActivity.EXTRA_TIME);

            Reminder reminder = new Reminder(name, description, priority, date, time);
            reminderViewModel.insert(reminder);
            Toast.makeText(this, "Reminder saved", Toast.LENGTH_SHORT).show();
        }
        else if (requestCode == EDIT_REM_REQUEST && resultCode == RESULT_OK)
        {
            int id = data.getIntExtra(AddEditReminderActivity.EXTRA_ID, -1);
            if (id == -1)
            {
                Toast.makeText(this, "Reminder cant be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(AddEditReminderActivity.EXTRA_NAME);
            String description = data.getStringExtra(AddEditReminderActivity.EXTRA_DESCRIPTION);
            int priority = data.getIntExtra(AddEditReminderActivity.EXTRA_PRIORITY, 1);
            String date = data.getStringExtra(AddEditReminderActivity.EXTRA_DATE);
            String time = data.getStringExtra(AddEditReminderActivity.EXTRA_TIME);

            Reminder reminder = new Reminder(name, description, priority, date, time);
            reminder.setId(id);
            reminderViewModel.update(reminder);

            Toast.makeText(this, "Reminder updated", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Reminder not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.delete_all_reminders:
                reminderViewModel.deleteAllReminders();
                Toast.makeText(this, "All reminders deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
