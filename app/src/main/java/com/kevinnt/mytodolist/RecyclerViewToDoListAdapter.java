package com.kevinnt.mytodolist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RecyclerViewToDoListAdapter extends RecyclerView.Adapter<RecyclerViewToDoListAdapter.ViewHolder>{

    private ArrayList<Item> item = new ArrayList<>();
    private Context context;
    private DatabaseHandler db;

    public RecyclerViewToDoListAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         db = new DatabaseHandler(context);

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cb_todo_item.setText(item.get(position).getTodoText());
        holder.cb_todo_item.setChecked(item.get(position).isChecked());
        if(item.get(position).isChecked()){
            holder.cb_todo_item.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.cb_todo_item.setBackgroundResource(R.drawable.layout_item_bg);
        }
        else {
            holder.cb_todo_item.setPaintFlags(holder.cb_todo_item.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.cb_todo_item.setBackgroundResource(0);
        }

        holder.cb_todo_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(holder.cb_todo_item.isChecked()){
                        holder.cb_todo_item.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        item.get(position).setChecked(true);
                        holder.cb_todo_item.setBackgroundResource(R.drawable.layout_item_bg);
                        db.updateCheckItem(item.get(position),context);
//                        notifyItemChanged(position);
                    }
                    else {
                        holder.cb_todo_item.setPaintFlags(holder.cb_todo_item.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                        item.get(position).setChecked(false);
                        holder.cb_todo_item.setBackgroundResource(0);
                        db.updateCheckItem(item.get(position),context);
//                        notifyItemChanged(position);
                    }
//                    Toast.makeText(context, "CHECK KEGANTI "+ position, Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Toast.makeText(context, "Error "+ e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public void setItem(ArrayList<Item> item) {
        this.item = item;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cb_todo_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_todo_item = itemView.findViewById(R.id.cb_todo_item);
        }
    }


    public void deleteItem(int position) {

        db.deleteTask(item.get(position));
        this.setItem(db.getAllTask());
        notifyDataSetChanged();
    }

}