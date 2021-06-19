package com.bawp.WGU;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.bawp.WGU.model.TermViewModel;

public class TermDetails extends AppCompatActivity {
    private TextView termTitle;
    private TextView termStart;
    private TextView termEnd;
    private int termId = 0;

    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        termTitle = findViewById(R.id.textView_term_title);
        termStart = findViewById(R.id.textView_start_date);
        termEnd = findViewById(R.id.textView_end_date);

        termViewModel = new ViewModelProvider.AndroidViewModelFactory(TermDetails.this
                .getApplication())
                .create(TermViewModel.class);

        termViewModel.get(termId).observe(this, term -> {
            if (term != null) {
                termTitle.setText(term.getName());
                termStart.setText(term.getTerm_start());
                termEnd.setText(term.getTerm_end());
            }
        });
    }
}