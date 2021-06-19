package com.bawp.WGU;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bawp.WGU.adapter.TermList;
import com.bawp.WGU.model.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Terms extends AppCompatActivity implements TermList.OnTermClickListener {

    private static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = "Clicked";
    public static final String TERM_ID = "term_id";
    private TermViewModel termViewModel;
    private RecyclerView recyclerView;
    private TermList termList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        recyclerView = findViewById(R.id.term_lists);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        termViewModel = new ViewModelProvider.AndroidViewModelFactory(Terms.this
                .getApplication())
                .create(TermViewModel.class);

        termViewModel.getAllTerms().observe(this, terms -> {
            termList = new TermList(terms, Terms.this, this);
            recyclerView.setAdapter(termList);
        });

        FloatingActionButton fab = findViewById(R.id.add_term_fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(Terms.this, Term.class);
            startActivityForResult(intent, NEW_TERM_ACTIVITY_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TERM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String term_title = data.getStringExtra(Term.TERM_TITLE_REPLY);
            String term_start = data.getStringExtra(Term.TERM_START);
            String term_end = data.getStringExtra(Term.TERM_END);

            assert term_title != null;
            com.bawp.WGU.model.Term term = new com.bawp.WGU.model.Term(term_title, term_start, term_end);

            TermViewModel.insert(term);
        }
    }

    @Override
    public void onTermClick(int position) {
        com.bawp.WGU.model.Term term = Objects.requireNonNull(termViewModel.allTerms.getValue()).get(position);
        Log.d(TAG, "onTermClick: " + term.getTerm_id());

        Intent intent = new Intent(Terms.this, Term.class);
        intent.putExtra(TERM_ID, term.getTerm_id());
        startActivity(intent);

    }
}