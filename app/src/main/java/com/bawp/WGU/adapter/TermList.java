package com.bawp.WGU.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawp.WGU.R;
import com.bawp.WGU.model.Term;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TermList extends RecyclerView.Adapter<TermList.ViewHolder> {
    private final List<Term> termList;
    private final OnTermClickListener termClickListener;

    public TermList(List<Term> termList, Context context, OnTermClickListener onTermClickListener) {
        this.termList = termList;
        this.termClickListener = onTermClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.term_row, parent, false);

        return new ViewHolder(view, termClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Term term = Objects.requireNonNull(termList.get(position));
        holder.termTitle.setText(term.getTerm_title());
        holder.termStart.setText(term.getTerm_start());
        holder.termEnd.setText(term.getTerm_end());
    }

    @Override
    public int getItemCount() {
        return termList.size();
    }

    public interface OnTermClickListener {
        void onTermClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView termTitle;;
        public TextView termStart;
        public TextView termEnd;
        OnTermClickListener onTermClickListener;

        public ViewHolder(@NonNull View itemView, OnTermClickListener onTermClickListener) {
            super(itemView);
            termTitle = itemView.findViewById(R.id.row_term_title);
            termStart = itemView.findViewById(R.id.row_term_start);
            termEnd = itemView.findViewById(R.id.row_term_end);
            this.onTermClickListener = onTermClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onTermClickListener.onTermClick(getAdapterPosition());
        }
    }
}
