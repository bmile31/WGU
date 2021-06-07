package com.bawp.WGU.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.bawp.WGU.model.Term;

@Dao
public interface TermDao {
    // CRUD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Term term);

    @Query("DELETE FROM TERM_TABLE")
    void deleteAll();

    @Query("SELECT * FROM TERM_TABLE ORDER BY name ASC")
    LiveData<List<Term>> getAllTerms();

    @Query("SELECT * FROM TERM_TABLE WHERE TERM_TABLE.id == :id")
    LiveData<Term> get(int id);

    @Update
    void update(Term term);

    @Delete
    void delete(Term term);
}
