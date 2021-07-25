package com.bawp.WGU.model;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.bawp.WGU.data.AssessmentRepository;
import com.bawp.WGU.data.InstructorRepository;

import java.util.List;

public class InstructorViewModel extends AndroidViewModel {

    public static InstructorRepository repository;
    public final LiveData<List<Instructor>> allInstructors;

    public InstructorViewModel(@NonNull Application application) {
        super(application);
        repository = new InstructorRepository(application);
        allInstructors = repository.getAllData();
    }

    public LiveData<List<Instructor>> getAllInstructors() { return allInstructors; }
    public static void insert(Instructor instructor) { repository.insert(instructor); }

    public LiveData<Instructor> get(int id) { return repository.get(id);}

    public LiveData<List<Instructor>> getInstructorsByCourse(int course_id){
        return repository.getInstructorsByCourse(course_id);
    }
    
    public static void update(Instructor instructor) { repository.update(instructor);}
    public static void delete(Instructor instructor) { repository.delete(instructor);}

}
