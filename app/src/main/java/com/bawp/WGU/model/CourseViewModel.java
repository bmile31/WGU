package com.bawp.WGU.model;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bawp.WGU.data.CourseRepository;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    public static CourseRepository repository;
    public final LiveData<List<Course>> allCourses;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseRepository(application);
        allCourses = repository.getAllData();
    }

    public LiveData<List<Course>> getAllCourses() { return allCourses; }
    public static void insert(Course course) { repository.insert(course); }

    public LiveData<Course> get(int id) { return repository.get(id);}
    
    public static void update(Course course) { repository.update(course);}
    public static void delete(Course course) { repository.delete(course);}

}
