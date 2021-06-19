package com.bawp.WGU.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bawp.WGU.model.Course;
import com.bawp.WGU.util.Database;

import java.util.List;

public class CourseRepository {
    private final CourseDao courseDao;
    private final LiveData<List<Course>> allCourses;

    public CourseRepository(Application application) {
        Database db = Database.getDatabase(application);
        courseDao = db.courseDao();

        allCourses = courseDao.getAllCourses();

    }
    public LiveData<List<Course>> getAllData() { return allCourses; }
    public void insert(Course course) {
         Database.databaseWriteExecutor.execute(() -> courseDao.insert(course));
    }
    public LiveData<Course> get(int course_id) {
         return courseDao.get(course_id);
    }
    public void update(Course course) {
        Database.databaseWriteExecutor.execute(() -> courseDao.update(course));
    }
    public void delete(Course course) {
         Database.databaseWriteExecutor.execute(() -> courseDao.delete(course));
    }

}
