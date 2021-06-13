package com.bawp.WGU;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bawp.WGU.adapter.RecyclerViewAdapter;
import com.bawp.WGU.model.Term;
import com.bawp.WGU.model.TermViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnTermClickListener {

    private static final int NEW_TERM_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = "Clicked";
    public static final String TERM_ID = "term_id";
    private TermViewModel termViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        termViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(TermViewModel.class);

        termViewModel.getAllTerms().observe(this, terms -> {
            recyclerViewAdapter = new RecyclerViewAdapter(terms, MainActivity.this, this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });

        FloatingActionButton fab = findViewById(R.id.add_term_fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewTerm.class);
            startActivityForResult(intent, NEW_TERM_ACTIVITY_REQUEST_CODE);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TERM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String name = data.getStringExtra(NewTerm.NAME_REPLY);
            String term_start = data.getStringExtra(NewTerm.TERM_START);
            String term_end = data.getStringExtra(NewTerm.TERM_END);

            assert name != null;
            Term term = new Term(name, term_start, term_end);

            TermViewModel.insert(term);
        }
    }

    @Override
    public void onTermClick(int position) {
        Term term = Objects.requireNonNull(termViewModel.allTerms.getValue()).get(position);
        Log.d(TAG, "onTermClick: " + term.getId());

        Intent intent = new Intent(MainActivity.this, NewTerm.class);
        intent.putExtra(TERM_ID, term.getId());
        startActivity(intent);

    }
}