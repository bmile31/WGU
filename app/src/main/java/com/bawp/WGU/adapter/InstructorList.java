package com.bawp.WGU.adapter;

import android.content.Context;
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

public class InstructorList extends RecyclerView.Adapter<InstructorList.ViewHolder> {
    private final List<Instructor> instructorList;
    private final OnInstructorClickListener instructorClickListener;

    public InstructorList(List<Instructor> instructorList, Context context, OnInstructorClickListener onInstructorClickListener) {
        this.instructorList = instructorList;
        this.instructorClickListener = onInstructorClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instructor_row, parent, false);

        return new ViewHolder(view, instructorClickListener);
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

    public interface OnInstructorClickListener {
        void onInstructorClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView instructorName;
        public TextView instructorEmail;
        public TextView instructorNumber;
        OnInstructorClickListener onInstructorClickListener;

        public ViewHolder(@NonNull View itemView, OnInstructorClickListener onInstructorClickListener) {
            super(itemView);
            instructorName = itemView.findViewById(R.id.row_instructor_name);
            instructorEmail = itemView.findViewById(R.id.row_instructor_email);
            instructorNumber = itemView.findViewById(R.id.row_instructor_number);
            this.onInstructorClickListener = onInstructorClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onInstructorClickListener.onInstructorClick(getAdapterPosition());
        }
    }
}
