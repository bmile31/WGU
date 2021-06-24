package com.bawp.WGU.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bawp.WGU.model.Course;

import java.util.List;

@Dao
public interface CourseDao {
    // CRUD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Course course);

    @Query("DELETE FROM COURSE_TABLE")
    void deleteAll();

    @Query("SELECT * FROM COURSE_TABLE ORDER BY course_title ASC")
    LiveData<List<Course>> getAllCourses();

    @Query("SELECT * FROM COURSE_TABLE WHERE COURSE_TABLE.course_id == :course_id")
    LiveData<Course> get(int course_id);

//    @Query("SELECT * FROM COURSE_TABLE WHERE term_id == :term_id")
//    LiveData<Course> get(int term_id);

    @Update
    void update(Course course);

    @Delete
    void delete(Course course);
}
