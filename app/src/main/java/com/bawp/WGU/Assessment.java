package com.bawp.WGU;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bawp.WGU.model.AssessmentViewModel;
import com.bawp.WGU.util.ReminderBroadcast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Assessment extends AppCompatActivity {
    public static final String ASSESSMENT_TITLE_REPLY = "assessment_title_reply";
    public static final String ASSESSMENT_END = "assessment_end";
    public static final String ASSESSMENT_TYPE = "assessment_type";
    private EditText enterAssessmentTitle;
    private EditText enterAssessmentEnd;
    private DatePickerDialog enterAssessmentEndDatepicker;
    private RadioGroup enterAssessmentType;
    private RadioButton selectedType;
    private RadioButton radioPerformance;
    private RadioButton radioObjective;
    private Button saveInfoButton;
    private int assessmentId = 0;
    private int courseId = 0;
    private Boolean isEdit = false;
    private Button updateButton;
    private Button deleteButton;

    private AssessmentViewModel assessmentViewModel;

    private void datePickers(){

        enterAssessmentEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar endDateCalendar = Calendar.getInstance();
                int courseEndYear = endDateCalendar.get(Calendar.YEAR); // current year
                int courseEndMonth = endDateCalendar.get(Calendar.MONTH); // current month
                int courseEndDay = endDateCalendar.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                enterAssessmentEndDatepicker = new DatePickerDialog(Assessment.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                enterAssessmentEnd.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, courseEndYear, courseEndMonth, courseEndDay);
                enterAssessmentEndDatepicker.show();
            }
        });

    }

    private void createNotificationChannel() {

        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationManager notificationManager = getSystemService(NotificationManager.class);

        CharSequence endDateName = "endNotifyDateAssessment";
        String endDateDescription = "Channel for end date reminder";
        NotificationChannel endDateChannel = new NotificationChannel("endNotifyDateAssessment", endDateName, importance);
        endDateChannel.setDescription(endDateDescription);

        notificationManager.createNotificationChannel(endDateChannel);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        ActionBar actionBar = getSupportActionBar();

        enterAssessmentTitle = findViewById(R.id.enter_assessment_title);
        enterAssessmentEnd = findViewById(R.id.enter_assessment_end);
        enterAssessmentType = findViewById(R.id.radio_type);
        radioObjective = findViewById(R.id.radioObjective);
        radioPerformance = findViewById(R.id.radioPerformance);
        saveInfoButton = findViewById(R.id.save_assessment_button);

        datePickers();
        createNotificationChannel();

        FloatingActionButton fab = findViewById(R.id.edit_assessment_fab);
        fab.setOnClickListener(view -> {
            enterAssessmentTitle.setEnabled(true);
            enterAssessmentEnd.setEnabled(true);
//            enterAssessmentType.setEnabled(true);
            radioObjective.setEnabled(true);
            radioPerformance.setEnabled(true);
            updateButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);

            assert actionBar != null;
            actionBar.setTitle("Edit Assessment");
        });

        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(Assessment.this
                .getApplication())
                .create(AssessmentViewModel.class);

        if (getIntent().hasExtra(Assessments.ASSESSMENT_ID)) {
            assessmentId = getIntent().getIntExtra(Assessments.ASSESSMENT_ID, 0);
            courseId = getIntent().getIntExtra(Assessments.COURSE_ID, 0);

            assessmentViewModel.get(assessmentId).observe(this, assessment -> {
                if (assessment != null) {
                    enterAssessmentTitle.setText(assessment.getAssessment_title());
                    enterAssessmentEnd.setText(assessment.getAssessment_end());
//                    enterAssessmentType.setText(assessment.getAssessment_type());
                    assessment.setCourse_id(assessment.getCourse_id());

                    if (assessment.getAssessment_type().equals("Performance assessment")) {
                        enterAssessmentType.check(R.id.radioPerformance);
                    }
                    else if (assessment.getAssessment_type().equals("Objective assessment")) {
                        enterAssessmentType.check(R.id.radioObjective);
                    }

                    assert actionBar != null;
                    actionBar.setTitle(assessment.getAssessment_title());
                }
            });
            isEdit = true;
        }

        saveInfoButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            Intent intent = new Intent(Assessment.this, ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(Assessment.this, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            selectedType = (RadioButton) findViewById(enterAssessmentType.getCheckedRadioButtonId());
            String selectedText = selectedType.getText().toString();

            if (!TextUtils.isEmpty(enterAssessmentTitle.getText())
                    && !TextUtils.isEmpty(enterAssessmentEnd.getText())) {
                String assessmentTitle = enterAssessmentTitle.getText().toString();
                String assessmentEnd = enterAssessmentEnd.getText().toString();
                String assessmentType = selectedText;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                long longEndDate = 0;
                try {
                    Date endDate = sdf.parse(assessmentEnd);
                    longEndDate = endDate.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                alarmManager.set(AlarmManager.RTC_WAKEUP, longEndDate, pendingIntent);

                replyIntent.putExtra(ASSESSMENT_TITLE_REPLY, assessmentTitle);
                replyIntent.putExtra(ASSESSMENT_END, assessmentEnd);
                replyIntent.putExtra(ASSESSMENT_TYPE, assessmentType);
                setResult(RESULT_OK, replyIntent);

            } else {
                setResult(RESULT_CANCELED, replyIntent);
            }
            finish();

        });

        deleteButton = findViewById(R.id.delete_assessment_button);
        deleteButton.setOnClickListener(view -> edit(true));
        //Update button
        updateButton = findViewById(R.id.update_assessment_button);
        updateButton.setOnClickListener(view -> edit(false));

        if (isEdit) {
            enterAssessmentTitle.setEnabled(false);
            enterAssessmentEnd.setEnabled(false);
//            enterAssessmentType.setEnabled(false);
            radioPerformance.setEnabled(false);
            radioObjective.setEnabled(false);
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
        Intent courseIDIntent = getIntent();
        courseId = courseIDIntent.getIntExtra("COURSE_ID", courseId);

        String assessmentTitle = enterAssessmentTitle.getText().toString().trim();
        String assessmentEnd = enterAssessmentEnd.getText().toString().trim();
//        String assessmentType = enterAssessmentType.getText().toString().trim();

        selectedType = (RadioButton) findViewById(enterAssessmentType.getCheckedRadioButtonId());
        String selectedText = selectedType.getText().toString();

        if (selectedText.equals("Performance assessment")) {
            enterAssessmentType.check(R.id.radioPerformance);
        }
        else if (selectedText.equals("Objective assessment")) {
            enterAssessmentType.check(R.id.radioObjective);
        }

        if (TextUtils.isEmpty(assessmentTitle) || TextUtils.isEmpty(assessmentEnd)) {
            Snackbar.make(enterAssessmentTitle, R.string.empty, Snackbar.LENGTH_SHORT)
                    .show();
        } else {
            com.bawp.WGU.model.Assessment assessment = new com.bawp.WGU.model.Assessment();
            assessment.setAssessment_id(assessmentId);
            assessment.setAssessment_title(assessmentTitle);
            assessment.setAssessment_end(assessmentEnd);
            assessment.setAssessment_type(selectedText);
            assessment.setCourse_id(courseId);
            if (isDelete)
                AssessmentViewModel.delete(assessment);
            else
                AssessmentViewModel.update(assessment);
            finish();

        }
    }
}