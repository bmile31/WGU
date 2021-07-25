package com.bawp.WGU.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bawp.WGU.model.Assessment;
import com.bawp.WGU.model.Instructor;

import java.util.List;

@Dao
public interface InstructorDao {
    // CRUD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Instructor instructor);

    @Query("DELETE FROM INSTRUCTOR_TABLE")
    void deleteAll();

    @Query("SELECT * FROM INSTRUCTOR_TABLE ORDER BY instructor_name ASC")
    LiveData<List<Instructor>> getAllInstructors();

    @Query("SELECT * FROM INSTRUCTOR_TABLE WHERE INSTRUCTOR_TABLE.instructor_id == :instructor_id")
    LiveData<Instructor> get(int instructor_id);

    @Query("SELECT * FROM INSTRUCTOR_TABLE WHERE course_id = :courseID")
    LiveData<List<Instructor>> getInstructorsByCourse(int courseID);

    @Update
    void update(Instructor instructor);

    @Delete
    void delete(Instructor instructor);
}
