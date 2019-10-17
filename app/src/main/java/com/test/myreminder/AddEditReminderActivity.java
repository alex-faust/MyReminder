package com.test.myreminder;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddEditReminderActivity extends AppCompatActivity
{
    public static final String EXTRA_ID =
            "com.test.myreminder.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.test.myreminder.EXTRA_NAME";
    public static final String EXTRA_DESCRIPTION =
            "com.test.myreminder.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.test.myreminder.EXTRA_PRIORITY";
    public static final String EXTRA_DATE =
            "com.test.myreminder.EXTRA_DATE";
    public static final String EXTRA_TIME =
            "com.test.myreminder.EXTRA_TIME";

    private EditText editName, editDescription;
    private NumberPicker numberPickerPriority;
    Button dateBtn, timeBtn;
    TimePickerDialog timePicker;
    Calendar calendar = Calendar.getInstance();
    TextView timeText, dateText;
    int day, month, year, hour, minutes;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.activity_add_reminder);

        editName = findViewById(R.id.edit_name_title);
        editDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        dateBtn = findViewById(R.id.dateButton);
        timeBtn = findViewById(R.id.timeButton);
        dateText = findViewById(R.id.dateText);
        timeText = findViewById(R.id.timeText);

        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.check);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID))
        {
            setTitle("Edit Reminder");
            editName.setText(intent.getStringExtra(EXTRA_NAME));
            editDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));
            dateText.setText(intent.getStringExtra(EXTRA_DATE));
            timeText.setText(intent.getStringExtra(EXTRA_TIME));
        }
        else
        {
            setTitle("Add Reminder");
        }

        timeBtn.setOnClickListener(v ->
        {
            timePicker = new TimePickerDialog(AddEditReminderActivity.this, (view, hourOfDay, minute) ->
                    timeText.setText(hourOfDay + ":" + minute), hour, minutes, false);
            timePicker.show();
        });

        dateBtn.setOnClickListener(v ->
        {
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);

            dateSetListener = (view, year1, month1, day1) -> {
                String date = (month1 + 1) + "/" + day1 + "/" + year1;
                dateText.setText(date);
            };

            DatePickerDialog dialog = new DatePickerDialog(AddEditReminderActivity.this,
                    AlertDialog.THEME_HOLO_LIGHT, dateSetListener,
                    year, month, day);
            dialog.show();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_rem_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.save_reminder:
                saveReminder();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveReminder()
    {
        String name = editName.getText().toString();
        String description = editDescription.getText().toString();
        int priority = numberPickerPriority.getValue();
        String date = dateText.getText().toString();
        String time = timeText.getText().toString();

        if (name.trim().isEmpty() || description.trim().isEmpty() || date.trim().isEmpty() || time.trim().isEmpty())
        {
            Toast.makeText(this, "Please enter all information", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            Intent data = new Intent();
            data.putExtra(EXTRA_NAME, name);
            data.putExtra(EXTRA_DESCRIPTION, description);
            data.putExtra(EXTRA_PRIORITY, priority);
            data.putExtra(EXTRA_DATE, date);
            data.putExtra(EXTRA_TIME, time);

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
            if (id != -1)
            {
                data.putExtra(EXTRA_ID, id);
            }
            setResult(RESULT_OK, data);
            finish();
        }
    }
}