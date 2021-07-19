package com.bawp.WGU;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.bawp.WGU.adapter.CourseList;
import com.bawp.WGU.adapter.CoursesInTermList;
import com.bawp.WGU.model.CourseViewModel;
import com.bawp.WGU.model.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Term extends AppCompatActivity {
    private static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
    public static final String TERM_TITLE_REPLY = "term_title_reply";
    public static final String TERM_START = "term_start";
    public static final String TERM_END = "term_end";
    public static final String TERM_ID = "term_id";
    private EditText enterTermTitle;
    private EditText enterTermStart;
    private EditText enterTermEnd;
    private DatePickerDialog termStartDatePicker;
    private DatePickerDialog termEndDatePicker;
    private Button saveInfoButton;
    private RecyclerView courseView;
    private int termId = 0;
    private Boolean isEdit = false;
    private Button updateButton;
    private Button deleteButton;

    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;

    private CoursesInTermList coursesInTermList;

    private void datePickers(){
        enterTermStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar startDateCalendar = Calendar.getInstance();
                int termStartYear = startDateCalendar.get(Calendar.YEAR); // current year
                int termStartMonth = startDateCalendar.get(Calendar.MONTH); // current month
                int termStartDay = startDateCalendar.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                termStartDatePicker = new DatePickerDialog(Term.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                enterTermStart.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, termStartYear, termStartMonth, termStartDay);
                termStartDatePicker.show();
            }
        });

        enterTermEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar endDateCalendar = Calendar.getInstance();
                int termEndYear = endDateCalendar.get(Calendar.YEAR); // current year
                int termEndMonth = endDateCalendar.get(Calendar.MONTH); // current month
                int termEndDay = endDateCalendar.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                termEndDatePicker = new DatePickerDialog(Term.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                enterTermEnd.setText(dayOfMonth + "-"
                                        + (monthOfYear + 1) + "-" + year);

                            }
                        }, termEndYear, termEndMonth, termEndDay);
                termEndDatePicker.show();
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        ActionBar actionBar = getSupportActionBar();

        enterTermTitle = findViewById(R.id.enter_term_title);
        enterTermStart = findViewById(R.id.enter_term_start);
        enterTermEnd = findViewById(R.id.enter_term_end);
        saveInfoButton = findViewById(R.id.save_term_button);
        courseView = findViewById(R.id.rvCourses);

        courseView.setLayoutManager(new LinearLayoutManager(this));
        courseView.setHasFixedSize(true);

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(Term.this
                .getApplication())
                .create(CourseViewModel.class);

        courseViewModel.getCoursesByTerm(getIntent().getIntExtra(Terms.TERM_ID, 0)).observe(this, courses -> {
            coursesInTermList = new CoursesInTermList(courses);
            courseView.setAdapter(coursesInTermList);
        });

        datePickers();

        FloatingActionButton addCourseFab = findViewById(R.id.add_course_fab);
        addCourseFab.setOnClickListener(view -> {
            Intent intent = new Intent(Term.this, Course.class);
            startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
            intent.putExtra("TERM_ID", termId);
        });

        FloatingActionButton editFab = findViewById(R.id.edit_term_fab);

        editFab.setOnClickListener(view -> {
            enterTermTitle.setEnabled(true);
            enterTermStart.setEnabled(true);
            enterTermEnd.setEnabled(true);
            updateButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            courseView.setVisibility(View.GONE);
            editFab.setVisibility(View.GONE);
//            cancelFab.setVisibility(View.VISIBLE);

            assert actionBar != null;
            actionBar.setTitle("Edit Term");

        });

        termViewModel = new ViewModelProvider.AndroidViewModelFactory(Term.this
                .getApplication())
                .create(TermViewModel.class);

        if (getIntent().hasExtra(Terms.TERM_ID)) {
            termId = getIntent().getIntExtra(Terms.TERM_ID, 0);

            termViewModel.get(termId).observe(this, term -> {
                if (term != null) {
                    enterTermTitle.setText(term.getTerm_title());
                    enterTermStart.setText(term.getTerm_start());
                    enterTermEnd.setText(term.getTerm_end());

                    assert actionBar != null;
                    actionBar.setTitle(term.getTerm_title());
                }
            });
            isEdit = true;
        }

        saveInfoButton.setOnClickListener(view -> {
            Intent termIntent = new Intent();

            if (!TextUtils.isEmpty(enterTermTitle.getText())
                    && !TextUtils.isEmpty(enterTermStart.getText())
                    && !TextUtils.isEmpty(enterTermEnd.getText())) {
                String termTitle = enterTermTitle.getText().toString();
                String termStart = enterTermStart.getText().toString();
                String termEnd = enterTermEnd.getText().toString();

                termIntent.putExtra(TERM_TITLE_REPLY, termTitle);
                termIntent.putExtra(TERM_START, termStart);
                termIntent.putExtra(TERM_END, termEnd);
                termIntent.putExtra(TERM_ID, termId);
                setResult(RESULT_OK, termIntent);

            } else {
                setResult(RESULT_CANCELED, termIntent);
            }
            finish();

        });

        deleteButton = findViewById(R.id.delete_term_button);
        deleteButton.setOnClickListener(view -> edit(true));
        //Update button
        updateButton = findViewById(R.id.update_term_button);
        updateButton.setOnClickListener(view -> edit(false));

        if (isEdit) {
            enterTermTitle.setEnabled(false);
            enterTermStart.setEnabled(false);
            enterTermEnd.setEnabled(false);
            saveInfoButton.setVisibility(View.GONE);
            updateButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        } else {
            updateButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            editFab.setVisibility(View.GONE);
            courseView.setVisibility(View.GONE);
        }
    }

    private void edit(Boolean isDelete) {
        String termTitle = enterTermTitle.getText().toString().trim();
        String termStart = enterTermStart.getText().toString().trim();
        String termEnd = enterTermEnd.getText().toString().trim();

        if (TextUtils.isEmpty(termTitle) || TextUtils.isEmpty(termStart) || TextUtils.isEmpty(termEnd)) {
            Snackbar.make(enterTermTitle, R.string.empty, Snackbar.LENGTH_SHORT)
                    .show();
        } else {
            com.bawp.WGU.model.Term term = new com.bawp.WGU.model.Term();
            term.setTerm_id(termId);
            term.setTerm_title(termTitle);
            term.setTerm_start(termStart);
            term.setTerm_end(termEnd);
            if (isDelete)
                TermViewModel.delete(term);
            else
                TermViewModel.update(term);
            finish();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_COURSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String course_title = data.getStringExtra(Course.COURSE_TITLE_REPLY);
            String course_start = data.getStringExtra(Course.COURSE_START);
            String course_end = data.getStringExtra(Course.COURSE_END);
            String course_status = data.getStringExtra(Course.COURSE_STATUS);
            String course_note = data.getStringExtra(Course.COURSE_NOTE);
            int term_id = termId;

            assert course_title != null;
            com.bawp.WGU.model.Course course = new com.bawp.WGU.model.Course(course_title, course_start, course_end, course_status, term_id, course_note);

            CourseViewModel.insert(course);
        }
    }
}