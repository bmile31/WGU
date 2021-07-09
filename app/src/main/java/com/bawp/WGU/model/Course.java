package com.bawp.WGU.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "course_table",
        foreignKeys = @ForeignKey(
            entity = Term.class,
            parentColumns = "term_id",
            childColumns = "term_id",
            onDelete = CASCADE)
)
public class Course {

    @PrimaryKey(autoGenerate = true)
    private int course_id;

    @ColumnInfo(name = "term_id")
    private int term_id;

    @ColumnInfo(name = "course_title")
    private String course_title;

    @ColumnInfo(name = "course_start")
    private String course_start;

    @ColumnInfo(name = "course_end")
    private String course_end;

    @ColumnInfo(name = "course_status")
    private String course_status;

    @ColumnInfo(name = "course_note")
    private String course_note;

    public Course() {
    }

    public Course(@NonNull String course_title, String course_start, String course_end, String course_status, int term_id, String course_note) {
        this.course_title = course_title;
        this.course_start = course_start;
        this.course_end = course_end;
        this.course_status = course_status;
        this.term_id = term_id;
        this.course_note = course_note;
    }

    public int getCourse_id() {
        return course_id;
    }

    public int getTerm_id() {
        return term_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }

    public String getCourse_title() {
        return course_title;
    }

    public void setCourse_title(String course_title) {
        this.course_title = course_title;
    }

    public String getCourse_start() {
        return course_start;
    }

    public String getCourse_note() {
        return course_note;
    }

    public void setCourse_note(String course_note) {
        this.course_note = course_note;
    }

    public void setCourse_start(String course_start) {
        this.course_start = course_start;
    }

    public String getCourse_end() {
        return course_end;
    }

    public void setCourse_end(String course_end) {
        this.course_end = course_end;
    }

    public String getCourse_status() {
        return course_status;
    }

    public void setCourse_status(String course_status) {
        this.course_status = course_status;
    }
}
