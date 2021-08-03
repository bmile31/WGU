package com.bawp.WGU.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.WGU.R;
import com.bawp.WGU.model.Instructor;

import java.util.List;
import java.util.Objects;

public class InstructorInCourseList extends RecyclerView.Adapter<InstructorInCourseList.ViewHolder> {
    private final List<Instructor> instructorList;

    public InstructorInCourseList(List<Instructor> instructorList) {
        this.instructorList = instructorList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructor_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Instructor instructor = Objects.requireNonNull(instructorList.get(position));
        holder.instructorName.setText(instructor.getInstructor_name());
        holder.instructorEmail.setText(instructor.getInstructor_email());
        holder.instructorNumber.setText(instructor.getInstructor_number());
    }

    @Override
    public int getItemCount() {
        return instructorList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView instructorName;
        public TextView instructorEmail;
        public TextView instructorNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            instructorName = itemView.findViewById(R.id.row_instructor_name);
            instructorEmail = itemView.findViewById(R.id.row_instructor_email);
            instructorNumber = itemView.findViewById(R.id.row_instructor_number);
        }
    }
}
