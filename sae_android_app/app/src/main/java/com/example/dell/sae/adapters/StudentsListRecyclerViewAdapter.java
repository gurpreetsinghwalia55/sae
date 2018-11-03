package com.example.dell.sae.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dell.sae.R;
import com.github.akashandroid90.imageletter.MaterialLetterIcon;

public class StudentsListRecyclerViewAdapter extends RecyclerView.Adapter<StudentsListRecyclerViewAdapter.MyViewHolder> {
    private Context context;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.students_evaluations_list_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        switch (i % 10) {
            case 0:
                holder.studentName.setText("Gurpreet Singh Walia");
                holder.rollno.setText("101783015");
                holder.studentIcon.setLetter("G");
                holder.studentIcon.setShapeColor(ContextCompat.getColor(context, R.color.lightBlue));
                break;
            case 1:
                holder.studentName.setText("Barjinder Pal Singh");
                holder.rollno.setText("101783009");
                holder.studentIcon.setLetter("B");
                holder.studentIcon.setShapeColor(ContextCompat.getColor(context, R.color.pink));
                break;
            case 2:
                holder.studentName.setText("Gursukhab Singh");
                holder.rollno.setText("101783016");
                holder.studentIcon.setLetter("G");
                holder.studentIcon.setShapeColor(ContextCompat.getColor(context, R.color.yellow));
                break;
            case 3:
                holder.studentName.setText("Harjot Singh");
                holder.rollno.setText("101783017");
                holder.studentIcon.setLetter("H");
                holder.studentIcon.setShapeColor(ContextCompat.getColor(context, R.color.orange));
                break;
            case 4:
                holder.studentName.setText("Harjot Singh Jaswal");
                holder.rollno.setText("101783018");
                holder.studentIcon.setLetter("H");
                holder.studentIcon.setShapeColor(ContextCompat.getColor(context, R.color.green));
                break;
            case 5:
                holder.studentName.setText("Arshdeep Singh");
                holder.rollno.setText("101783005");
                holder.studentIcon.setLetter("A");
                holder.studentIcon.setShapeColor(ContextCompat.getColor(context, R.color.red));
                break;
            case 6:
                holder.studentName.setText("Abhishek Kumar");
                holder.rollno.setText("101783003");
                holder.studentIcon.setLetter("A");
                holder.studentIcon.setShapeColor(ContextCompat.getColor(context, R.color.violet));
                break;
            case 7:
                holder.studentName.setText("Sarthak Sharma");
                holder.rollno.setText("101783037");
                holder.studentIcon.setLetter("S");
                holder.studentIcon.setShapeColor(ContextCompat.getColor(context, R.color.red));
                break;
            case 8:
                holder.studentName.setText("Sutikshan Lakhanpal");
                holder.rollno.setText("101783041");
                holder.studentIcon.setLetter("S");
                holder.studentIcon.setShapeColor(ContextCompat.getColor(context, R.color.lightBlue));
                break;
            case 9:
                holder.studentName.setText("Bhargav Sood");
                holder.rollno.setText("101783012");
                holder.studentIcon.setLetter("B");
                holder.studentIcon.setShapeColor(ContextCompat.getColor(context, R.color.orange));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView studentName, rollno;
        MaterialLetterIcon studentIcon;
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
            rollno = itemView.findViewById(R.id.rollno);
            studentIcon = itemView.findViewById(R.id.studentIcon);
        }
    }
}
