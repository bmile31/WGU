package com.bawp.WGU.model;


import android.app.Application;

import com.bawp.WGU.data.TermRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class TermViewModel extends AndroidViewModel {

    public static TermRepository repository;
    public final LiveData<List<Term>> allTerms;

    public TermViewModel(@NonNull Application application) {
        super(application);
        repository = new TermRepository(application);
        allTerms = repository.getAllData();
    }

    public LiveData<List<Term>> getAllTerms() { return allTerms; }
    public static void insert(Term term) { repository.insert(term); }

    public LiveData<Term> get(int id) { return repository.get(id);}
    
    public static void update(Term term) { repository.update(term);}
    public static void delete(Term term) { repository.delete(term);}

}
