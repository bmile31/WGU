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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Term> termList;
    private final OnTermClickListener termClickListener;

    public RecyclerViewAdapter(List<Term> termList, Context context, OnTermClickListener onTermClickListener) {
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
        holder.name.setText(term.getName());
        holder.occupation.setText(term.getOccupation());


    }

    @Override
    public int getItemCount() {
        return termList.size();
    }

    public interface OnTermClickListener {
        void onTermClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView occupation;
        OnTermClickListener onTermClickListener;

        public ViewHolder(@NonNull View itemView, OnTermClickListener onTermClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.row_name_textview);
            occupation = itemView.findViewById(R.id.row_occupation_textview);
            this.onTermClickListener = onTermClickListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            onTermClickListener.onTermClick(getAdapterPosition());
        }
    }
}
