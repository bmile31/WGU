package com.bawp.WGU;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Button termsButton;
    private Button coursesButton;

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
    }

    public void openTerms(){
        Intent intent = new Intent(this, Terms.class);
        startActivity(intent);
    }

    public void openCourses(){
        Intent intent = new Intent(this, Courses.class);
        startActivity(intent);
    }
}