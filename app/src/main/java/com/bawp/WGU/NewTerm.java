package com.bawp.WGU;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bawp.WGU.model.Term;
import com.bawp.WGU.model.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class NewTerm extends AppCompatActivity {
    public static final String NAME_REPLY = "name_reply";
    public static final String TERM_START = "term_start";
    public static final String TERM_END = "term_end";
    private EditText enterName;
    private EditText enterTermStart;
    private EditText enterTermEnd;
    private Button saveInfoButton;
    private int termId = 0;
    private Boolean isEdit = false;
    private Boolean isNew = false;
    private Button updateButton;
    private Button deleteButton;

    private TermViewModel termViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_term);
        enterName = findViewById(R.id.enter_name);
        enterTermStart = findViewById(R.id.enter_termstart);
        enterTermEnd = findViewById(R.id.enter_termend);
        saveInfoButton = findViewById(R.id.save_button);

        FloatingActionButton fab = findViewById(R.id.edit_term_fab);
        fab.setOnClickListener(view -> {
            enterName.setEnabled(true);
            enterTermStart.setEnabled(true);
            enterTermEnd.setEnabled(true);
            updateButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
        });

        termViewModel = new ViewModelProvider.AndroidViewModelFactory(NewTerm.this
                .getApplication())
                .create(TermViewModel.class);

        if (getIntent().hasExtra(Terms.TERM_ID)) {
            termId = getIntent().getIntExtra(Terms.TERM_ID, 0);

            termViewModel.get(termId).observe(this, term -> {
                if (term != null) {
                    enterName.setText(term.getName());
                    enterTermStart.setText(term.getTerm_start());
                    enterTermEnd.setText(term.getTerm_end());
                }
            });
            isEdit = true;
        }

        saveInfoButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            if (!TextUtils.isEmpty(enterName.getText())
                    && !TextUtils.isEmpty(enterTermStart.getText())
                    && !TextUtils.isEmpty(enterTermEnd.getText())) {
                String name = enterName.getText().toString();
                String termStart = enterTermStart.getText().toString();
                String termEnd = enterTermEnd.getText().toString();

                replyIntent.putExtra(NAME_REPLY, name);
                replyIntent.putExtra(TERM_START, termStart);
                replyIntent.putExtra(TERM_END, termEnd);
                setResult(RESULT_OK, replyIntent);


            } else {
                setResult(RESULT_CANCELED, replyIntent);
            }
            finish();

        });

        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> edit(true));
        //Update button
        updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(view -> edit(false));


        if (isEdit) {
            enterName.setEnabled(false);
            enterTermStart.setEnabled(false);
            enterTermEnd.setEnabled(false);
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
        String name = enterName.getText().toString().trim();
        String termStart = enterTermStart.getText().toString().trim();
        String termEnd = enterTermEnd.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(termStart) || TextUtils.isEmpty(termEnd)) {
            Snackbar.make(enterName, R.string.empty, Snackbar.LENGTH_SHORT)
                    .show();
        } else {
            Term term = new Term();
            term.setId(termId);
            term.setName(name);
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