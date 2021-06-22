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

public class CourseList extends RecyclerView.Adapter<CourseList.ViewHolder> {
    private final List<Course> courseList;
    private final OnCourseClickListener courseClickListener;

    public CourseList(List<Course> courseList, Context context, OnCourseClickListener onCourseClickListener) {
        this.courseList = courseList;
        this.courseClickListener = onCourseClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_row, parent, false);

        return new ViewHolder(view, courseClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Course course = Objects.requireNonNull(courseList.get(position));
        holder.courseTitle.setText(course.getCourse_title());
        holder.courseStart.setText(course.getCourse_start());
        holder.courseEnd.setText(course.getCourse_end());
        holder.courseStatus.setText(course.getCourse_status());
    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public interface OnCourseClickListener {
        void onCourseClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView courseTitle;;
        public TextView courseStart;
        public TextView courseEnd;
        public TextView courseStatus;
        OnCourseClickListener onCourseClickListener;

        public ViewHolder(@NonNull View itemView, OnCourseClickListener onCourseClickListener) {
            super(itemView);
            courseTitle = itemView.findViewById(R.id.row_course_title);
            courseStart = itemView.findViewById(R.id.row_course_start);
            courseEnd = itemView.findViewById(R.id.row_course_end);
            courseStatus = itemView.findViewById(R.id.row_course_status);
            this.onCourseClickListener = onCourseClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onCourseClickListener.onCourseClick(getAdapterPosition());
        }
    }
}
