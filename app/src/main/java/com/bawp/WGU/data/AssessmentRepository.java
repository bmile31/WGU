package com.bawp.WGU.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bawp.WGU.model.Assessment;
import com.bawp.WGU.util.Database;

import java.util.List;

public class AssessmentRepository {
    private final AssessmentDao assessmentDao;
    private final LiveData<List<Assessment>> allAssessments;

    public AssessmentRepository(Application application) {
        Database db = Database.getDatabase(application);
        assessmentDao = db.assessmentDao();

        allAssessments = assessmentDao.getAllAssessments();

    }
    public LiveData<List<Assessment>> getAllData() { return allAssessments; }
    public void insert(Assessment assessment) {
         Database.databaseWriteExecutor.execute(() -> assessmentDao.insert(assessment));
    }
    public LiveData<Assessment> get(int assessment_id) {
         return assessmentDao.get(assessment_id);
    }
    public void update(Assessment assessment) {
        Database.databaseWriteExecutor.execute(() -> assessmentDao.update(assessment));
    }
    public void delete(Assessment assessment) {
         Database.databaseWriteExecutor.execute(() -> assessmentDao.delete(assessment));
    }

}
