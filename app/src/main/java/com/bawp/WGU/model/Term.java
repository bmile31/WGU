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
    private int term_id;

    @ColumnInfo(name = "term_title")
    private String term_title;

    @ColumnInfo(name = "term_start")
    private String term_start;

    @ColumnInfo(name = "term_end")
    private String term_end;

    public Term() {
    }

    public Term(@NonNull String term_title, String term_start, String term_end) {
        this.term_title = term_title;
        this.term_start = term_start;
        this.term_end = term_end;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public void setTerm_title(String term_title) {
        this.term_title = term_title;
    }

    public void setTerm_start(String term_start) { this.term_start = term_start; }

    public void setTerm_end(String term_end) { this.term_end = term_end; }

    public int getTerm_id() {
        return term_id;
    }

    public String getTerm_title() {
        return term_title;
    }

    public String getTerm_start() { return term_start; }

    public String getTerm_end() { return term_end; }

}
