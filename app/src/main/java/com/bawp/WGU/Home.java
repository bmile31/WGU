package com.bawp.WGU;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Button termsButton;
    private Button coursesButton;
    private Button assessmentButton;
    private Button instructorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        termsButton = findViewById(R.id.terms_button);
        termsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTerms();
            }
        });

        coursesButton = findViewById(R.id.courses_button);
        coursesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCourses();
            }
        });

        assessmentButton = findViewById(R.id.assessments_button);
        assessmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAssessments();
            }
        });

        instructorButton = findViewById(R.id.instructors_button);
        instructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInstructors();
            }
        });
    }

    public void openTerms(){
        Intent intent = new Intent(this, Terms.class);
        startActivity(intent);
    }

    public void openCourses(){
        Intent intent = new Intent(this, Courses.class);
        startActivity(intent);
    }

    public void openAssessments(){
        Intent intent = new Intent(this, Assessments.class);
        startActivity(intent);
    }

    public void openInstructors(){
        Intent intent = new Intent(this, Instructors.class);
        startActivity(intent);
    }
}