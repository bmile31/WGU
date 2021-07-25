package com.bawp.WGU.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bawp.WGU.model.Assessment;
import com.bawp.WGU.model.Instructor;
import com.bawp.WGU.model.Term;
import com.bawp.WGU.util.Database;

import java.util.List;

public class InstructorRepository {
    private final InstructorDao instructorDao;
    private final LiveData<List<Instructor>> allInstructors;

    public InstructorRepository(Application application) {
        Database db = Database.getDatabase(application);
        instructorDao = db.instructorDao();

        allInstructors = instructorDao.getAllInstructors();

    }
    public LiveData<List<Instructor>> getAllData() { return allInstructors; }
    public void insert(Instructor instructor) {
         Database.databaseWriteExecutor.execute(() -> instructorDao.insert(instructor));
    }
    public LiveData<List<Instructor>> getInstructorsByCourse(int courseID){
        return instructorDao.getInstructorsByCourse(courseID);
    }

    public LiveData<Instructor> get(int instructor_id) {
         return instructorDao.get(instructor_id);
    }
    public void update(Instructor instructor) {
        Database.databaseWriteExecutor.execute(() -> instructorDao.update(instructor));
    }
    public void delete(Instructor instructor) {
         Database.databaseWriteExecutor.execute(() -> instructorDao.delete(instructor));
    }

}
