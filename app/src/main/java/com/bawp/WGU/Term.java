package com.bawp.WGU;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import java.util.List;

public class Term extends AppCompatActivity {
    private static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
    public static final String TERM_TITLE_REPLY = "term_title_reply";
    public static final String TERM_START = "term_start";
    public static final String TERM_END = "term_end";
    public static final String TERM_ID = "term_id";
    public static final String COURSE_ID = "course_id";
    private EditText enterTermTitle;
    private EditText enterTermStart;
    private EditText enterTermEnd;
    private Button saveInfoButton;
    private RecyclerView courseView;
    private int termId = 0;
    private int courseId = 0;
    private Boolean isEdit = false;
    private Button updateButton;
    private Button deleteButton;
    private Button addCourseButton;

    private TermViewModel termViewModel;
    private CourseViewModel courseViewModel;

    private CoursesInTermList coursesInTermList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term);

        ActionBar actionBar = getSupportActionBar();

        enterTermTitle = findViewById(R.id.enter_term_title);
        enterTermStart = findViewById(R.id.enter_term_start);
        enterTermEnd = findViewById(R.id.enter_term_end);
        saveInfoButton = findViewById(R.id.save_term_button);
        addCourseButton = findViewById(R.id.add_course_button);
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

        FloatingActionButton addCourseFab = findViewById(R.id.add_course_fab);
        addCourseFab.setOnClickListener(view -> {
            Intent intent = new Intent(Term.this, Course.class);
            startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
            intent.putExtra("TERM_ID", termId);
        });

        FloatingActionButton editFab = findViewById(R.id.edit_term_fab);
//        FloatingActionButton cancelFab = findViewById(R.id.cancel_edit_term_fab);
//        cancelFab.setVisibility(View.GONE);
        editFab.setOnClickListener(view -> {
            enterTermTitle.setEnabled(true);
            enterTermStart.setEnabled(true);
            enterTermEnd.setEnabled(true);
            updateButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            addCourseButton.setVisibility(View.VISIBLE);
            courseView.setVisibility(View.GONE);
            editFab.setVisibility(View.GONE);
//            cancelFab.setVisibility(View.VISIBLE);

            assert actionBar != null;
            actionBar.setTitle("Edit Term");

        });

//        cancelFab.setOnClickListener(view -> {
//            enterTermTitle.setEnabled(false);
//            enterTermStart.setEnabled(false);
//            enterTermEnd.setEnabled(false);
//            updateButton.setVisibility(View.GONE);
//            deleteButton.setVisibility(View.GONE);
//            addCourseButton.setVisibility(View.GONE);
//            courseView.setVisibility(View.VISIBLE);
//            editFab.setVisibility(View.VISIBLE);
//            cancelFab.setVisibility(View.GONE);
//
//        });

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

        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Term.this, Course.class);
                intent.putExtra("TERM_ID", termId);
                startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
                startActivity(intent);
                Log.i(Course.TERM_ID, "Term ID on Term page: " + termId );
                Log.i(Courses.COURSE_ID, "Course ID on Term page: " + courseId );
            }
        });

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
            addCourseButton.setVisibility(View.GONE);
        } else {
            updateButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
            addCourseButton.setVisibility(View.GONE);
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
//            int term_id = data.getIntExtra(Course.TERM_ID, termId);

            int term_id = 2;
            Log.i(Course.TERM_ID, "Term ID on Term page: " + termId );
            Log.i(Courses.COURSE_ID, "Course ID on Term page: " + courseId );
//            String termID = data.getStringExtra(Course.TERM_ID);
//            int term_id = Integer.parseInt(termID);
//            int term_id = 1;

            assert course_title != null;
            com.bawp.WGU.model.Course course = new com.bawp.WGU.model.Course(course_title, course_start, course_end, course_status, term_id);

            CourseViewModel.insert(course);
        }
        else {
                    Log.i(Course.COURSE_TITLE_REPLY, "request code now working: " + requestCode + " result code: " + resultCode );

        }
    }
}