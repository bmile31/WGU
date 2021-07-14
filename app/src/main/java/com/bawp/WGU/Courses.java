package com.bawp.WGU;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.WGU.adapter.CourseList;
import com.bawp.WGU.model.CourseViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class Courses extends AppCompatActivity implements CourseList.OnCourseClickListener {

//    private static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
//    private static final String TAG = "Clicked";
    public static final String COURSE_ID = "course_id";
    public static final String TERM_ID = "term_id";
    private CourseViewModel courseViewModel;
    private RecyclerView recyclerView;
    private CourseList courseList;
//    int termId = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        recyclerView = findViewById(R.id.course_list);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        courseViewModel = new ViewModelProvider.AndroidViewModelFactory(Courses.this
                .getApplication())
                .create(CourseViewModel.class);

        courseViewModel.getAllCourses().observe(this, courses -> {
            courseList = new CourseList(courses, Courses.this, this);
            recyclerView.setAdapter(courseList);
        });

//        Log.i(Term.TERM_ID, "Term ID on Course: " + course.ge );

//        FloatingActionButton fab = findViewById(R.id.add_course_fab);
//        fab.setOnClickListener(view -> {
//            Intent intent = new Intent(Courses.this, Course.class);
//            startActivityForResult(intent, NEW_COURSE_ACTIVITY_REQUEST_CODE);
//        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        Log.i(Course.COURSE_TITLE_REPLY, "request code now working: " + requestCode + " result code: " + resultCode );
//
//        if (requestCode == NEW_COURSE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            assert data != null;
//            String course_title = data.getStringExtra(Course.COURSE_TITLE_REPLY);
//            String course_start = data.getStringExtra(Course.COURSE_START);
//            String course_end = data.getStringExtra(Course.COURSE_END);
//            String course_status = data.getStringExtra(Course.COURSE_STATUS);
////            int term_id = data.getIntExtra(Course.TERM_ID, 3);
////            String termID = data.getStringExtra(Course.TERM_ID);
////            int term_id = Integer.parseInt(termID);
//            int term_id = 3;
//
//            assert course_title != null;
//            com.bawp.WGU.model.Course course = new com.bawp.WGU.model.Course(course_title, course_start, course_end, course_status, term_id);
//
//            CourseViewModel.insert(course);
//        }
//    }

    @Override
    public void onCourseClick(int position) {
        com.bawp.WGU.model.Course course = Objects.requireNonNull(courseViewModel.allCourses.getValue()).get(position);
//        Log.d(TAG, "onCourseClick: " + course.getCourse_id());

        Intent intent = new Intent(Courses.this, Course.class);
        intent.putExtra(COURSE_ID, course.getCourse_id());
        intent.putExtra(TERM_ID, course.getTerm_id());
        startActivity(intent);

    }
}