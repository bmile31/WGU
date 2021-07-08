package com.bawp.WGU.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "assessment_table",
        foreignKeys = @ForeignKey(
                entity = Course.class,
                parentColumns = "course_id",
                childColumns = "course_id",
                onDelete = CASCADE))
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    private int assessment_id;

    @ColumnInfo(name = "course_id")
    private int course_id;

    @ColumnInfo(name = "assessment_title")
    private String assessment_title;

    @ColumnInfo(name = "assessment_end")
    private String assessment_end;

    @ColumnInfo(name = "assessment_type")
    private String assessment_type;

    public Assessment() {
    }

    public Assessment(@NonNull String assessment_title, String assessment_end, String assessment_type, int course_id) {
        this.assessment_title = assessment_title;
        this.assessment_end = assessment_end;
        this.assessment_type = assessment_type;
        this.course_id = course_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getAssessment_id() {
        return assessment_id;
    }

    public void setAssessment_id(int assessment_id) {
        this.assessment_id = assessment_id;
    }

    public String getAssessment_title() {
        return assessment_title;
    }

    public void setAssessment_title(String assessment_title) {
        this.assessment_title = assessment_title;
    }

    public String getAssessment_end() {
        return assessment_end;
    }

    public void setAssessment_end(String assessment_end) {
        this.assessment_end = assessment_end;
    }

    public String getAssessment_type() {
        return assessment_type;
    }

    public void setAssessment_type(String assessment_type) {
        this.assessment_type = assessment_type;
    }
}
