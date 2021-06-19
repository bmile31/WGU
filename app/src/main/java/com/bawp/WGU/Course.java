package com.bawp.WGU;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bawp.WGU.model.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class Course extends AppCompatActivity {
    public static final String COURSE_TITLE_REPLY = "course_title_reply";
    public static final String COURSE_START = "course_start";
    public static final String COURSE_END = "course_end";
    public static final String COURSE_STATUS = "course_status";
    private EditText enterCourseTitle;
    private EditText enterCourseStart;
    private EditText enterCourseEnd;
    private EditText enterCourseStatus;
    private Button saveInfoButton;
    private int courseId = 0;
    private Boolean isEdit = false;
    private Button updateButton;
    private Button deleteButton;

    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        ActionBar actionBar = getSupportActionBar();

        enterCourseTitle = findViewById(R.id.enter_course_title);
        enterCourseStart = findViewById(R.id.enter_course_start);
        enterCourseEnd = findViewById(R.id.enter_course_end);
        enterCourseStatus = findViewById(R.id.enter_course_status);
        saveInfoButton = findViewById(R.id.save_course_button);

        FloatingActionButton fab = findViewById(R.id.edit_course_fab);
        fab.setOnClickListener(view -> {
            enterCourseTitle.setEnabled(true);
            enterCourseStart.setEnabled(true);
            enterCourseEnd.setEnabled(true);
            enterCourseStatus.setEnabled(true);
            updateButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);

            assert actionBar != null;
            actionBar.setTitle("Edit Course");
        });

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(Course.this
                .getApplication())
                .create(CourseViewModel.class);

        if (getIntent().hasExtra(Courses.COURSE_ID)) {
            courseId = getIntent().getIntExtra(Courses.COURSE_ID, 0);

            courseViewModel.get(courseId).observe(this, course -> {
                if (course != null) {
                    enterCourseTitle.setText(course.getCourse_title());
                    enterCourseStart.setText(course.getCourse_start());
                    enterCourseEnd.setText(course.getCourse_end());
                    enterCourseStatus.setText(course.getCourse_status());

                    assert actionBar != null;
                    actionBar.setTitle(course.getCourse_title());
                }
            });
            isEdit = true;
        }

        saveInfoButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            if (!TextUtils.isEmpty(enterCourseTitle.getText())
                    && !TextUtils.isEmpty(enterCourseStart.getText())
                    && !TextUtils.isEmpty(enterCourseEnd.getText())
                    && !TextUtils.isEmpty(enterCourseStatus.getText())) {
                String courseTitle = enterCourseTitle.getText().toString();
                String courseStart = enterCourseStart.getText().toString();
                String courseEnd = enterCourseEnd.getText().toString();
                String courseStatus = enterCourseStatus.getText().toString();

                replyIntent.putExtra(COURSE_TITLE_REPLY, courseTitle);
                replyIntent.putExtra(COURSE_START, courseStart);
                replyIntent.putExtra(COURSE_END, courseEnd);
                replyIntent.putExtra(COURSE_STATUS, courseStatus);
                setResult(RESULT_OK, replyIntent);


            } else {
                setResult(RESULT_CANCELED, replyIntent);
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
        String courseTitle = enterCourseTitle.getText().toString().trim();
        String courseStart = enterCourseStart.getText().toString().trim();
        String courseEnd = enterCourseEnd.getText().toString().trim();
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
            if (isDelete)
                CourseViewModel.delete(course);
            else
                CourseViewModel.update(course);
            finish();

        }
    }
}