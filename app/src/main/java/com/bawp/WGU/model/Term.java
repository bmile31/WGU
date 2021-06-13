package com.bawp.WGU.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName = "term_table")
public class Term {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "term_start")
    private String term_start;

    @ColumnInfo(name = "term_end")
    private String term_end;

    public Term() {
    }

    public Term(@NonNull String name, String term_start, String term_end) {
        this.name = name;
        this.term_start = term_start;
        this.term_end = term_end;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTerm_start(String term_start) { this.term_start = term_start; }

    public void setTerm_end(String term_end) { this.term_end = term_end; }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTerm_start() { return term_start; }

    public String getTerm_end() { return term_end; }

}
