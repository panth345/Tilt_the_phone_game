package com.example.tilt_the_ball;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class scoreAdapterClass extends RecyclerView.Adapter<scoreAdapterClass.ViewHolder> {
    List<User> employee;
    Context context;
    DatabaseHelper databaseHelperClass;

    public scoreAdapterClass() {
        this.employee = employee;
        this.context = context;
        databaseHelperClass = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.score_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final User user = employee.get(position);

        holder.textViewID.setText((user.getName()));
        holder.textViewscore.setText(user.getScore());

    }
    @Override
    public int getItemCount() {
        return employee.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewID;
        EditText textViewscore;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewID = itemView.findViewById(R.id.text_name);
            textViewscore = itemView.findViewById(R.id.text_score);

        }
    }
}
