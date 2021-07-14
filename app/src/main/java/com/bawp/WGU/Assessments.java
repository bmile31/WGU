package com.bawp.WGU;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.WGU.adapter.AssessmentList;
import com.bawp.WGU.model.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class Assessments extends AppCompatActivity implements AssessmentList.OnAssessmentClickListener {

//    private static final int NEW_ASSESSMENT_ACTIVITY_REQUEST_CODE = 1;
//    private static final String TAG = "Clicked";
    public static final String ASSESSMENT_ID = "assessment_id";
    public static final String COURSE_ID = "course_id";
    private AssessmentViewModel assessmentViewModel;
    private RecyclerView recyclerView;
    private AssessmentList assessmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessments);

        recyclerView = findViewById(R.id.assessment_list);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(Assessments.this
                .getApplication())
                .create(AssessmentViewModel.class);

        assessmentViewModel.getAllAssessments().observe(this, assessments -> {
            assessmentList = new AssessmentList(assessments, Assessments.this, this);
            recyclerView.setAdapter(assessmentList);
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
    public void onAssessmentClick(int position) {
        com.bawp.WGU.model.Assessment assessment = Objects.requireNonNull(assessmentViewModel.allAssessments.getValue()).get(position);
//        Log.d(TAG, "onAssessmentClick: " + assessment.getAssessment_id());

        Intent intent = new Intent(Assessments.this, Assessment.class);
        intent.putExtra(ASSESSMENT_ID, assessment.getAssessment_id());
        intent.putExtra(COURSE_ID, assessment.getCourse_id());
        startActivity(intent);

    }
}