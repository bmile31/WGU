package com.bawp.WGU.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.WGU.R;
import com.bawp.WGU.model.Assessment;

import java.util.List;

public class AssessmentsInCourseList extends RecyclerView.Adapter<AssessmentsInCourseList.ViewHolder> {
    private final List<Assessment> assessmentsInCourseList;

    public AssessmentsInCourseList(List<Assessment> assessmentsInCourseList) {
        this.assessmentsInCourseList = assessmentsInCourseList;
    }

    @NonNull
    @Override
    public AssessmentsInCourseList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_row, parent, false);

        return new AssessmentsInCourseList.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentsInCourseList.ViewHolder holder, int position) {
        Assessment assessment = assessmentsInCourseList.get(position);

        holder.assessmentTitle.setText(assessment.getAssessment_title());
        holder.assessmentEnd.setText(assessment.getAssessment_end());
        holder.assessmentType.setText(assessment.getAssessment_type());
    }

    @Override
    public int getItemCount() {
        return assessmentsInCourseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView assessmentTitle;
        public TextView assessmentEnd;
        public TextView assessmentType;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            assessmentTitle = itemView.findViewById(R.id.row_assessment_title);
            assessmentEnd = itemView.findViewById(R.id.row_assessment_end);
            assessmentType = itemView.findViewById(R.id.row_assessment_type);
        }
    }
}
