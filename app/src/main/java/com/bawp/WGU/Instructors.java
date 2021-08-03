package com.bawp.WGU;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.WGU.adapter.InstructorList;
import com.bawp.WGU.model.Instructor;
import com.bawp.WGU.model.InstructorViewModel;

import java.util.Objects;

public class Instructors extends AppCompatActivity implements InstructorList.OnInstructorClickListener {

//    private static final int NEW_COURSE_ACTIVITY_REQUEST_CODE = 1;
//    private static final String TAG = "Clicked";
    public static final String INSTRUCTOR_ID = "instructor_id";
    public static final String COURSE_ID = "course_id";
    private InstructorViewModel instructorViewModel;
    private RecyclerView recyclerView;
    private InstructorList instructorList;
//    int termId = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructors);

        recyclerView = findViewById(R.id.instructor_list);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        instructorViewModel = new ViewModelProvider.AndroidViewModelFactory(Instructors.this
                .getApplication())
                .create(InstructorViewModel.class);

        instructorViewModel.getAllInstructors().observe(this, instructors -> {
            instructorList = new InstructorList(instructors, Instructors.this, this);
            recyclerView.setAdapter(instructorList);
        });

        //        FloatingActionButton fab = findViewById(R.id.add_assessment_fab);
//        fab.setOnClickListener(view -> {
//            Intent intent = new Intent(Assessments.this, Assessment.class);
//            startActivityForResult(intent, NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE);
//        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            assert data != null;
//            String assessment_title = data.getStringExtra(Assessment.ASSESSMENT_TITLE_REPLY);
//            String assessment_end = data.getStringExtra(Assessment.ASSESSMENT_END);
//            String assessment_type = data.getStringExtra(Assessment.ASSESSMENT_TYPE);
//            int course_id = 2;
//
//            assert assessment_title != null;
//            com.bawp.WGU.model.Assessment assessment = new com.bawp.WGU.model.Assessment(assessment_title, assessment_end, assessment_type, course_id);
//
//            AssessmentViewModel.insert(assessment);
//        }
//    }

    @Override
    public void onInstructorClick(int position) {
//        com.bawp.WGU.model.Instructor instructor = Objects.requireNonNull(instructorViewModel.allInstructors.getValue()).get(position);
        com.bawp.WGU.model.Instructor instructor = Objects.requireNonNull(instructorViewModel.allInstructors.getValue().get(position));
//        Log.d(TAG, "onCourseClick: " + course.getCourse_id());

        Intent intent = new Intent(Instructors.this, com.bawp.WGU.Instructor.class);
        intent.putExtra(INSTRUCTOR_ID, instructor.getInstructor_id());
        intent.putExtra(COURSE_ID, instructor.getCourse_id());
        startActivity(intent);

    }
}