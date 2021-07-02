package com.e.notepad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.BeanHolder> {


    private List<Note> List;
    private Context context;

    private LayoutInflater LayoutInflater;
    private OnNoteItemClick onNoteItemClick;

    public NotesAdapter(List<Note> list,Context context) {
        LayoutInflater = android.view.LayoutInflater.from(context);
        this.List = list;
        this.context=context;
        this.onNoteItemClick = (OnNoteItemClick)context;
    }
    public interface OnNoteItemClick
    {
        void onNoteClick(int pos);
    }

    @NonNull

    @Override
    public BeanHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.inflate(R.layout.note_list_item,parent,false);
        return new BeanHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  NotesAdapter.BeanHolder holder, int position) {

        holder.textViewTitle.setText(List.get(position).getTitle());
        holder.textViewContent.setText(List.get(position).getContent());


    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public class BeanHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    { TextView textViewContent;
      TextView textViewTitle;
        public BeanHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewContent=itemView.findViewById(R.id.item_text);
            textViewTitle=itemView.findViewById(R.id.tv_title);

        }

        @Override
        public void onClick(View view) {
          onNoteItemClick.onNoteClick(getAbsoluteAdapterPosition());

        }
    }
}
