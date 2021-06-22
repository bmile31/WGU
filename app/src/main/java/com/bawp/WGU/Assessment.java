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

import com.bawp.WGU.model.AssessmentViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class Assessment extends AppCompatActivity {
    public static final String ASSESSMENT_TITLE_REPLY = "assessment_title_reply";
    public static final String ASSESSMENT_END = "assessment_end";
    public static final String ASSESSMENT_TYPE = "assessment_type";
    private EditText enterAssessmentTitle;
    private EditText enterAssessmentEnd;
    private EditText enterAssessmentType;
    private Button saveInfoButton;
    private int assessmentId = 0;
    private Boolean isEdit = false;
    private Button updateButton;
    private Button deleteButton;

    private AssessmentViewModel assessmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment);

        ActionBar actionBar = getSupportActionBar();

        enterAssessmentTitle = findViewById(R.id.enter_assessment_title);
        enterAssessmentEnd = findViewById(R.id.enter_assessment_end);
        enterAssessmentType = findViewById(R.id.enter_assessment_type);
        saveInfoButton = findViewById(R.id.save_assessment_button);

        FloatingActionButton fab = findViewById(R.id.edit_assessment_fab);
        fab.setOnClickListener(view -> {
            enterAssessmentTitle.setEnabled(true);
            enterAssessmentEnd.setEnabled(true);
            enterAssessmentType.setEnabled(true);
            updateButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);

            assert actionBar != null;
            actionBar.setTitle("Edit Assessment");
        });

        assessmentViewModel = new ViewModelProvider.AndroidViewModelFactory(Assessment.this
                .getApplication())
                .create(AssessmentViewModel.class);

        if (getIntent().hasExtra(Assessments.ASSESSMENT_ID)) {
            assessmentId = getIntent().getIntExtra(Assessments.ASSESSMENT_ID, 0);

            assessmentViewModel.get(assessmentId).observe(this, assessment -> {
                if (assessment != null) {
                    enterAssessmentTitle.setText(assessment.getAssessment_title());
                    enterAssessmentEnd.setText(assessment.getAssessment_end());
                    enterAssessmentType.setText(assessment.getAssessment_type());

                    assert actionBar != null;
                    actionBar.setTitle(assessment.getAssessment_title());
                }
            });
            isEdit = true;
        }

        saveInfoButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            if (!TextUtils.isEmpty(enterAssessmentTitle.getText())
                    && !TextUtils.isEmpty(enterAssessmentEnd.getText())
                    && !TextUtils.isEmpty(enterAssessmentType.getText())) {
                String assessmentTitle = enterAssessmentTitle.getText().toString();
                String assessmentEnd = enterAssessmentEnd.getText().toString();
                String assessmentType = enterAssessmentType.getText().toString();

                replyIntent.putExtra(ASSESSMENT_TITLE_REPLY, assessmentTitle);
                replyIntent.putExtra(ASSESSMENT_END, assessmentEnd);
                replyIntent.putExtra(ASSESSMENT_TYPE, assessmentType);
                setResult(RESULT_OK, replyIntent);


            } else {
                setResult(RESULT_CANCELED, replyIntent);
            }
            finish();

        });

        deleteButton = findViewById(R.id.delete_assessment_button);
        deleteButton.setOnClickListener(view -> edit(true));
        //Update button
        updateButton = findViewById(R.id.update_assessment_button);
        updateButton.setOnClickListener(view -> edit(false));


        if (isEdit) {
            enterAssessmentTitle.setEnabled(false);
            enterAssessmentEnd.setEnabled(false);
            enterAssessmentType.setEnabled(false);
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
        String assessmentTitle = enterAssessmentTitle.getText().toString().trim();
        String assessmentEnd = enterAssessmentEnd.getText().toString().trim();
        String assessmentType = enterAssessmentType.getText().toString().trim();

        if (TextUtils.isEmpty(assessmentTitle) || TextUtils.isEmpty(assessmentEnd) || TextUtils.isEmpty(assessmentType)) {
            Snackbar.make(enterAssessmentTitle, R.string.empty, Snackbar.LENGTH_SHORT)
                    .show();
        } else {
            com.bawp.WGU.model.Assessment assessment = new com.bawp.WGU.model.Assessment();
            assessment.setAssessment_id(assessmentId);
            assessment.setAssessment_title(assessmentTitle);
            assessment.setAssessment_end(assessmentEnd);
            assessment.setAssessment_type(assessmentType);
            if (isDelete)
                AssessmentViewModel.delete(assessment);
            else
                AssessmentViewModel.update(assessment);
            finish();

        }
    }
}