package com.bawp.WGU.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "instructor_table",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "course_id",
                childColumns = "course_id",
                onDelete = CASCADE))
public class Instructor {

    @PrimaryKey(autoGenerate = true)
    private int instructor_id;

    @ColumnInfo(name = "course_id")
    private int course_id;

    @ColumnInfo(name = "instructor_name")
    private String instructor_name;

    @ColumnInfo(name = "instructor_email")
    private String instructor_email;

    @ColumnInfo(name = "instructor_number")
    private String instructor_number;

    public Instructor() {
    }

    public Instructor(@NonNull String instructor_name, String instructor_email, String instructor_number, int course_id) {
        this.instructor_name = instructor_name;
        this.instructor_email = instructor_email;
        this.instructor_number = instructor_number;
        this.course_id = course_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(int instructor_id) {
        this.instructor_id = instructor_id;
    }

    public String getInstructor_name() {
        return instructor_name;
    }

    public void setInstructor_name(String instructor_name) {
        this.instructor_name = instructor_name;
    }

    public String getInstructor_email() {
        return instructor_email;
    }

    public void setInstructor_email(String instructor_email) {
        this.instructor_email = instructor_email;
    }

    public String getInstructor_number() {
        return instructor_number;
    }

    public void setInstructor_number(String instructor_number) {
        this.instructor_number = instructor_number;
    }
}
