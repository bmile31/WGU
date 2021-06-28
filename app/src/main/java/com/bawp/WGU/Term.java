package com.bawp.WGU;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bawp.WGU.adapter.CourseList;
import com.bawp.WGU.adapter.CoursesInTermList;
import com.bawp.WGU.model.Course;
import com.bawp.WGU.model.CourseViewModel;
import com.bawp.WGU.model.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

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
    public static final String TERM_TITLE_REPLY = "term_title_reply";
    public static final String TERM_START = "term_start";
    public static final String TERM_END = "term_end";
    private EditText enterTermTitle;
    private EditText enterTermStart;
    private EditText enterTermEnd;
    private Button saveInfoButton;
    private RecyclerView courseView;
    private int termId = 0;
    private Boolean isEdit = false;
    private Button updateButton;
    private Button deleteButton;

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

        FloatingActionButton fab = findViewById(R.id.edit_term_fab);
        fab.setOnClickListener(view -> {
            enterTermTitle.setEnabled(true);
            enterTermStart.setEnabled(true);
            enterTermEnd.setEnabled(true);
            updateButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            courseView.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);

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
            Intent replyIntent = new Intent();

            if (!TextUtils.isEmpty(enterTermTitle.getText())
                    && !TextUtils.isEmpty(enterTermStart.getText())
                    && !TextUtils.isEmpty(enterTermEnd.getText())) {
                String termTitle = enterTermTitle.getText().toString();
                String termStart = enterTermStart.getText().toString();
                String termEnd = enterTermEnd.getText().toString();

                replyIntent.putExtra(TERM_TITLE_REPLY, termTitle);
                replyIntent.putExtra(TERM_START, termStart);
                replyIntent.putExtra(TERM_END, termEnd);
                setResult(RESULT_OK, replyIntent);


            } else {
                setResult(RESULT_CANCELED, replyIntent);
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
            fab.setVisibility(View.GONE);
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
}