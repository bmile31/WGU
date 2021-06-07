package com.bawp.WGU.data;

import android.app.Application;

import com.bawp.WGU.model.Term;
import com.bawp.WGU.util.TermRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TermRepository {
    private TermDao termDao;
    private LiveData<List<Term>> allTerms;

    public TermRepository(Application application) {
        TermRoomDatabase db = TermRoomDatabase.getDatabase(application);
        termDao = db.termDao();

        allTerms = termDao.getAllTerms();

    }
    public LiveData<List<Term>> getAllData() { return allTerms; }
    public void insert(Term term) {
         TermRoomDatabase.databaseWriteExecutor.execute(() -> termDao.insert(term));
    }
    public LiveData<Term> get(int id) {
         return termDao.get(id);
    }
    public void update(Term term) {
        TermRoomDatabase.databaseWriteExecutor.execute(() -> termDao.update(term));
    }
    public void delete(Term term) {
         TermRoomDatabase.databaseWriteExecutor.execute(() -> termDao.delete(term));
    }

}
