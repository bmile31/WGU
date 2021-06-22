package com.bawp.WGU.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bawp.WGU.model.Assessment;

import java.util.List;

@Dao
public interface AssessmentDao {
    // CRUD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Assessment assessment);

    @Query("DELETE FROM ASSESSMENT_TABLE")
    void deleteAll();

    @Query("SELECT * FROM ASSESSMENT_TABLE ORDER BY assessment_title ASC")
    LiveData<List<Assessment>> getAllAssessments();

    @Query("SELECT * FROM ASSESSMENT_TABLE WHERE ASSESSMENT_TABLE.assessment_id == :assessment_id")
    LiveData<Assessment> get(int assessment_id);

    @Update
    void update(Assessment assessment);

    @Delete
    void delete(Assessment assessment);
}
