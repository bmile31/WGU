package com.bawp.WGU;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bawp.WGU.model.InstructorViewModel;
import com.bawp.WGU.util.ReminderBroadcast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Instructor extends AppCompatActivity {
    public static final String INSTRUCTOR_NAME_REPLY = "instructor_name_reply";
    public static final String INSTRUCTOR_EMAIL = "instructor_email";
    public static final String INSTRUCTOR_NUMBER = "instructor_number";
    private EditText enterInstructorName;
    private EditText enterInstructorEmail;
    private EditText enterInstructorNumber;
    private Button saveInfoButton;
    private int instructorId = 0;
    private int courseId = 0;
    private Boolean isEdit = false;
    private Button updateButton;
    private Button deleteButton;

    private InstructorViewModel instructorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor);

        ActionBar actionBar = getSupportActionBar();

        enterInstructorName = findViewById(R.id.enter_instructor_name);
        enterInstructorEmail = findViewById(R.id.enter_instructor_email);
        enterInstructorNumber = findViewById(R.id.enter_instructor_number);
        saveInfoButton = findViewById(R.id.save_instructor_button);

        FloatingActionButton fab = findViewById(R.id.edit_instructor_fab);
        fab.setOnClickListener(view -> {
            enterInstructorName.setEnabled(true);
            enterInstructorNumber.setEnabled(true);
            enterInstructorEmail.setEnabled(true);
            updateButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);

            assert actionBar != null;
            actionBar.setTitle("Edit Instructor");
        });

        instructorViewModel = new ViewModelProvider.AndroidViewModelFactory(Instructor.this
                .getApplication())
                .create(InstructorViewModel.class);

        if (getIntent().hasExtra(Instructors.INSTRUCTOR_ID)) {
            instructorId = getIntent().getIntExtra(Instructors.INSTRUCTOR_ID, 0);
            courseId = getIntent().getIntExtra(Instructors.COURSE_ID, 0);

            instructorViewModel.get(instructorId).observe(this, instructor -> {
                if (instructor != null) {
                    enterInstructorName.setText(instructor.getInstructor_name());
                    enterInstructorEmail.setText(instructor.getInstructor_email());
                    enterInstructorNumber.setText(instructor.getInstructor_number());
                    instructor.setInstructor_id(instructor.getInstructor_id());


                    assert actionBar != null;
                    actionBar.setTitle(instructor.getInstructor_name());
                }
            });
            isEdit = true;
        }

        saveInfoButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
//
//            Intent intent = new Intent(Instructor.this, ReminderBroadcast.class);
//            PendingIntent pendingIntent = PendingIntent.getBroadcast(Instructor.this, 0, intent, 0);
//            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

//            String selectedText = selectedType.getText().toString();

            if (!TextUtils.isEmpty(enterInstructorName.getText())
                    && !TextUtils.isEmpty(enterInstructorEmail.getText())
                    && !TextUtils.isEmpty(enterInstructorNumber.getText())) {
                String instructorName = enterInstructorName.getText().toString();
                String instructorEmail = enterInstructorEmail.getText().toString();
                String instructorNumber = enterInstructorNumber.getText().toString();


                replyIntent.putExtra(INSTRUCTOR_NAME_REPLY, instructorName);
                replyIntent.putExtra(INSTRUCTOR_EMAIL, instructorEmail);
                replyIntent.putExtra(INSTRUCTOR_NUMBER, instructorNumber);
                setResult(RESULT_OK, replyIntent);

            } else {
                setResult(RESULT_CANCELED, replyIntent);
            }
            finish();

        });

        deleteButton = findViewById(R.id.delete_instructor_button);
        deleteButton.setOnClickListener(view -> edit(true));
        //Update button
        updateButton = findViewById(R.id.update_instructor_button);
        updateButton.setOnClickListener(view -> edit(false));

        if (isEdit) {
            enterInstructorName.setEnabled(false);
            enterInstructorEmail.setEnabled(false);
            enterInstructorNumber.setEnabled(false);
            saveInfoButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        } else {
            updateButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        }

    }

    private void edit(Boolean isDelete) {
        Intent instructorIDIntent = getIntent();
        instructorId = instructorIDIntent.getIntExtra("COURSE_ID", courseId);

        String instructorName = enterInstructorName.getText().toString().trim();
        String instructorEmail = enterInstructorEmail.getText().toString().trim();
        String instructorNumber = enterInstructorNumber.getText().toString().trim();

        if (TextUtils.isEmpty(instructorName) || TextUtils.isEmpty(instructorEmail) || TextUtils.isEmpty(instructorNumber)) {
            Snackbar.make(enterInstructorName, R.string.empty, Snackbar.LENGTH_SHORT)
                    .show();
        } else {
            com.bawp.WGU.model.Instructor instructor = new com.bawp.WGU.model.Instructor();
            instructor.setInstructor_id(instructorId);
            instructor.setInstructor_name(instructorName);
            instructor.setInstructor_email(instructorEmail);
            instructor.setInstructor_number(instructorNumber);
            instructor.setCourse_id(courseId);
            if (isDelete)
                InstructorViewModel.delete(instructor);
            else
                InstructorViewModel.update(instructor);
            finish();

        }
    }
}