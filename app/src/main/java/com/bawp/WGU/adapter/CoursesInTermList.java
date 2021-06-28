package com.bawp.WGU.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.WGU.R;
import com.bawp.WGU.model.Course;

import java.util.List;
import java.util.Objects;

public class CoursesInTermList extends RecyclerView.Adapter<CoursesInTermList.ViewHolder> {
    private final List<Course> coursesInTermList;

    public CoursesInTermList(List<Course> coursesInTermList) {
        this.coursesInTermList = coursesInTermList;
    }

    @NonNull
    @Override
    public CoursesInTermList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_row, parent, false);

        return new CoursesInTermList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoursesInTermList.ViewHolder holder, int position) {
        Course course = coursesInTermList.get(position);
        holder.courseTitle.setText(course.getCourse_title());
        holder.courseStart.setText(course.getCourse_start());
        holder.courseEnd.setText(course.getCourse_end());
        holder.courseStatus.setText(course.getCourse_status());
    }

    @Override
    public int getItemCount() {
        return coursesInTermList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView courseTitle;
        public TextView courseStart;
        public TextView courseEnd;
        public TextView courseStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.row_course_title);
            courseStart = itemView.findViewById(R.id.row_course_start);
            courseEnd = itemView.findViewById(R.id.row_course_end);
            courseStatus = itemView.findViewById(R.id.row_course_status);
        }
    }
}
