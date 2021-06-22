package com.bawp.WGU.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.WGU.R;
import com.bawp.WGU.model.Assessment;

import java.util.List;
import java.util.Objects;

public class AssessmentList extends RecyclerView.Adapter<AssessmentList.ViewHolder> {
    private final List<Assessment> assessmentList;
    private final OnAssessmentClickListener assessmentClickListener;

    public AssessmentList(List<Assessment> assessmentList, Context context, OnAssessmentClickListener onAssessmentClickListener) {
        this.assessmentList = assessmentList;
        this.assessmentClickListener = onAssessmentClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.assessment_row, parent, false);

        return new ViewHolder(view, assessmentClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Assessment assessment = Objects.requireNonNull(assessmentList.get(position));
        holder.assessmentTitle.setText(assessment.getAssessment_title());
        holder.assessmentEnd.setText(assessment.getAssessment_end());
        holder.assessmentType.setText(assessment.getAssessment_type());
    }

    @Override
    public int getItemCount() {
        return assessmentList.size();
    }

    public interface OnAssessmentClickListener {
        void onAssessmentClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView assessmentTitle;
        public TextView assessmentEnd;
        public TextView assessmentType;
        OnAssessmentClickListener onAssessmentClickListener;

        public ViewHolder(@NonNull View itemView, OnAssessmentClickListener onAssessmentClickListener) {
            super(itemView);
            assessmentTitle = itemView.findViewById(R.id.row_assessment_title);
            assessmentEnd = itemView.findViewById(R.id.row_assessment_end);
            assessmentType = itemView.findViewById(R.id.row_assessment_type);
            this.onAssessmentClickListener = onAssessmentClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onAssessmentClickListener.onAssessmentClick(getAdapterPosition());
        }
    }
}
