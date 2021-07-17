package com.bawp.WGU;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.WGU.adapter.AssessmentsInCourseList;
import com.bawp.WGU.model.CourseViewModel;
import com.bawp.WGU.model.AssessmentViewModel;

import com.bawp.WGU.model.Term;
import com.bawp.WGU.model.TermViewModel;
import com.bawp.WGU.util.ReminderBroadcast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Course extends AppCompatActivity {
    private static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;
    public static final String COURSE_TITLE_REPLY = "course_title_reply";
    public static final String COURSE_START = "course_start";
    public static final String COURSE_END = "course_end";
    public static final String COURSE_STATUS = "course_status";
    public static final String COURSE_NOTE = "course_note";
    public static final String TERM_ID = "term_id";
    private static final String TAG = "Clicked";
    private EditText enterCourseTitle;
    private EditText enterCourseStart;
    private EditText enterCourseEnd;
    private DatePickerDialog enterCourseStartDatepicker;
    private DatePickerDialog enterCourseEndDatepicker;
    private EditText enterCourseStatus;
    private EditText enterCourseNote;
    private Button saveInfoButton;
    private RecyclerView assessmentsView;
    private int courseId = 0;
    private int termId = 0;
    private Boolean isEdit = false;
    private Button updateButton;
    private Button deleteButton;
    private FloatingActionButton addAssessmentFab;
    private FloatingActionButton editCourseFab;
    private FloatingActionButton shareFab;

    private CourseViewModel courseViewModel;
    private AssessmentViewModel assessmentViewModel;

    private AssessmentsInCourseList assessmentsInCourseList;

    private void datePickers(){
        enterCourseStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar startDateCalendar = Calendar.getInstance();
                int courseStartYear = startDateCalendar.get(Calendar.YEAR); // current year
                int courseStartMonth = startDateCalendar.get(Calendar.MONTH); // current month
                int courseStartDay = startDateCalendar.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                enterCourseStartDatepicker = new DatePickerDialog(Course.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                enterCourseStart.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, courseStartYear, courseStartMonth, courseStartDay);
                enterCourseStartDatepicker.show();
            }
        });

        enterCourseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar endDateCalendar = Calendar.getInstance();
                int courseEndYear = endDateCalendar.get(Calendar.YEAR); // current year
                int courseEndMonth = endDateCalendar.get(Calendar.MONTH); // current month
                int courseEndDay = endDateCalendar.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                enterCourseEndDatepicker = new DatePickerDialog(Course.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                enterCourseEnd.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, courseEndYear, courseEndMonth, courseEndDay);
                enterCourseEndDatepicker.show();
            }
        });

    }

    private void createNotificationChannel() {

        CharSequence startDateName = "startNotifyDate";
        String startDateDescription = "Channel for start date reminder";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel startDateChannel = new NotificationChannel("startNotifyDate", startDateName, importance);
        startDateChannel.setDescription(startDateDescription);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(startDateChannel);

        CharSequence endDateName = "endNotifyDate";
        String endDateDescription = "Channel for end date reminder";
        NotificationChannel endDateChannel = new NotificationChannel("endNotifyDate", endDateName, importance);
        startDateChannel.setDescription(endDateDescription);

        notificationManager.createNotificationChannel(endDateChannel);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        ActionBar actionBar = getSupportActionBar();

        enterCourseTitle = findViewById(R.id.enter_course_title);
        enterCourseEnd = findViewById(R.id.enter_course_end);
        enterCourseStart = findViewById(R.id.enter_course_start);
        enterCourseStatus = findViewById(R.id.enter_course_status);
        enterCourseNote = findViewById(R.id.enter_course_note);
        saveInfoButton = findViewById(R.id.save_course_button);
        assessmentsView = findViewById(R.id.rvAssessments);
        addAssessmentFab = findViewById(R.id.add_assessment_fab);
        editCourseFab = findViewById(R.id.edit_course_fab);
        shareFab = findViewById(R.id.share_fab);

        assessmentsView.setLayoutManager(new LinearLayoutManager(this));
        assessmentsView.setHasFixedSize(true);

        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(Course.this
                .getApplication())
                .create(AssessmentViewModel.class);

        assessmentViewModel.getAssessmentsByCourse(getIntent().getIntExtra(Courses.COURSE_ID, 0)).observe(this, assessments -> {
            assessmentsInCourseList = new AssessmentsInCourseList(assessments);
            assessmentsView.setAdapter(assessmentsInCourseList);
        });

        datePickers();

        createNotificationChannel();
//        enterCourseEnd.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
//            @Override
//            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
//                Calendar today = Calendar.getInstance();
////                if (year == today.get(Calendar.YEAR)
////                        && month == today.get(Calendar.MONTH)
////                        && dayOfMonth == today.get(Calendar.DAY_OF_MONTH))
////                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pendingIntent);
//
//            }
//        });

//        assessmentViewModel.getAllAssessments().observe(this, assessments -> {
//            assessmentsInCourseList = new AssessmentsInCourseList(assessments);
//            assessmentsView.setAdapter(assessmentsInCourseList);
//        });
        editCourseFab.setOnClickListener(view -> {
            enterCourseTitle.setEnabled(true);
            enterCourseStart.setEnabled(true);
            enterCourseEnd.setEnabled(true);
            enterCourseStatus.setEnabled(true);
            enterCourseNote.setEnabled(true);
            updateButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            editCourseFab.setVisibility(View.GONE);
            addAssessmentFab.setVisibility(View.GONE);
            shareFab.setVisibility(View.GONE);

            assert actionBar != null;
            actionBar.setTitle("Edit Course");

        });

        addAssessmentFab.setOnClickListener(view -> {
            Intent intent = new Intent(Course.this, Assessment.class);
            startActivityForResult(intent, NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
            intent.putExtra("COURSE_ID", courseId);
        });

        shareFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareSub = enterCourseTitle.getText().toString() + " Notes";
                String shareNote = enterCourseNote.getText().toString();
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareNote);
                startActivity(Intent.createChooser(shareIntent, "Share notes using"));
            }
        });

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(Course.this
                .getApplication())
                .create(CourseViewModel.class);

        Intent termIDIntent = getIntent();

        if (getIntent().hasExtra(Courses.COURSE_ID)) {
            courseId = getIntent().getIntExtra(Courses.COURSE_ID, 0);
            termId = getIntent().getIntExtra(Courses.TERM_ID, 0);

            courseViewModel.get(courseId).observe(this, course -> {
                if (course != null) {
                    enterCourseTitle.setText(course.getCourse_title());
                    enterCourseStart.setText(course.getCourse_start());
                    enterCourseEnd.setText(course.getCourse_end());
                    enterCourseStatus.setText(course.getCourse_status());
                    enterCourseNote.setText(course.getCourse_note());
                    course.setTerm_id(course.getTerm_id());

                    assert actionBar != null;
                    actionBar.setTitle(course.getCourse_title());
                }
            });
            isEdit = true;
        }

        saveInfoButton.setOnClickListener(view -> {
            Intent courseIntent = new Intent();
            termId = termIDIntent.getIntExtra("TERM_ID", termId);

            Intent intent = new Intent(Course.this, ReminderBroadcast.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(Course.this, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long timeAtButtonClick = System.currentTimeMillis();
            long tenSecondsInMillis = 1000 * 10;

            if (!TextUtils.isEmpty(enterCourseTitle.getText())
                    && !TextUtils.isEmpty(enterCourseStart.getText())
                    && !TextUtils.isEmpty(enterCourseEnd.getText())
                    && !TextUtils.isEmpty(enterCourseStatus.getText())) {
                String courseTitle = enterCourseTitle.getText().toString();
                String courseStart = enterCourseStart.getText().toString();
                String courseEnd = enterCourseEnd.getText().toString();
                String courseStatus = enterCourseStatus.getText().toString();
                String courseNote = enterCourseNote.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                long longStartDate = 0;
                long longEndDate = 0;
                try {
                    Date startDate = sdf.parse(courseStart);
                    Date endDate = sdf.parse(courseEnd);
                    longStartDate = startDate.getTime();
                    longEndDate = endDate.getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                alarmManager.set(AlarmManager.RTC_WAKEUP, longStartDate, pendingIntent);
                alarmManager.set(AlarmManager.RTC_WAKEUP, longEndDate, pendingIntent);

                courseIntent.putExtra(COURSE_TITLE_REPLY, courseTitle);
                courseIntent.putExtra(COURSE_START, courseStart);
                courseIntent.putExtra(COURSE_END, courseEnd);
                courseIntent.putExtra(COURSE_STATUS, courseStatus);
                courseIntent.putExtra(TERM_ID, termId);
                courseIntent.putExtra(COURSE_NOTE, courseNote);
                setResult(RESULT_OK, courseIntent);
            } else {
                setResult(RESULT_CANCELED, courseIntent);
            }
            finish();
        });

        deleteButton = findViewById(R.id.delete_course_button);
        deleteButton.setOnClickListener(view -> edit(true));
        //Update button
        updateButton = findViewById(R.id.update_course_button);
        updateButton.setOnClickListener(view -> edit(false));

        if (isEdit) {
            enterCourseTitle.setEnabled(false);
            enterCourseStart.setEnabled(false);
            enterCourseEnd.setEnabled(false);
            enterCourseStatus.setEnabled(false);
            enterCourseNote.setEnabled(false);
            saveInfoButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        } else {
            updateButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            editCourseFab.setVisibility(View.GONE);
            shareFab.setVisibility(View.GONE);
        }
    }

    private void edit(Boolean isDelete) {

        String courseTitle = enterCourseTitle.getText().toString().trim();
        String courseStart = enterCourseStart.getText().toString().trim();
        String courseEnd = enterCourseEnd.getText().toString().trim();
        String courseNote = enterCourseNote.getText().toString().trim();
        String courseStatus = enterCourseStatus.getText().toString().trim();

        if (TextUtils.isEmpty(courseTitle) || TextUtils.isEmpty(courseStart) || TextUtils.isEmpty(courseEnd) || TextUtils.isEmpty(courseStatus)) {
            Snackbar.make(enterCourseTitle, R.string.empty, Snackbar.LENGTH_SHORT)
                    .show();
        } else {

            com.bawp.WGU.model.Course course = new com.bawp.WGU.model.Course();
            course.setCourse_id(courseId);
            course.setCourse_title(courseTitle);
            course.setCourse_start(courseStart);
            course.setCourse_end(courseEnd);
            course.setCourse_status(courseStatus);
            course.setCourse_note(courseNote);
            course.setTerm_id(termId);

            if (isDelete)
                CourseViewModel.delete(course);
            else
                CourseViewModel.update(course);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String assessment_title = data.getStringExtra(Assessment.ASSESSMENT_TITLE_REPLY);
            String assessment_end = data.getStringExtra(Assessment.ASSESSMENT_END);
            String assessment_type = data.getStringExtra(Assessment.ASSESSMENT_TYPE);
            int course_id = courseId;

            assert assessment_title != null;
            com.bawp.WGU.model.Assessment assessment = new com.bawp.WGU.model.Assessment(assessment_title, assessment_end, assessment_type, course_id);

            AssessmentViewModel.insert(assessment);
        }
    }
}