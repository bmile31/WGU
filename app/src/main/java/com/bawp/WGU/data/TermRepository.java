package com.bawp.WGU.data;

import android.app.Application;

import com.bawp.WGU.model.Term;
import com.bawp.WGU.util.Database;

import java.util.List;

import androidx.lifecycle.LiveData;

public class TermRepository {
    private final TermDao termDao;
    private final LiveData<List<Term>> allTerms;

    public TermRepository(Application application) {
        Database db = Database.getDatabase(application);
        termDao = db.termDao();

        allTerms = termDao.getAllTerms();

    }
    public LiveData<List<Term>> getAllData() { return allTerms; }
    public void insert(Term term) {
         Database.databaseWriteExecutor.execute(() -> termDao.insert(term));
    }
    public LiveData<Term> get(int term_id) {
         return termDao.get(term_id);
    }
    public void update(Term term) {
        Database.databaseWriteExecutor.execute(() -> termDao.update(term));
    }
    public void delete(Term term) {
         Database.databaseWriteExecutor.execute(() -> termDao.delete(term));
    }

}
