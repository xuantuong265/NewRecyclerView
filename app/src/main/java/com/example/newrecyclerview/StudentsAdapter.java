package com.example.newrecyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StudentsAdapter extends RecyclerView.Adapter<StudentsAdapter.ViewHolder> {

    ArrayList<Students> personArrayList;
    Context context;

    public StudentsAdapter(ArrayList<Students> personArrayList, Context context) {
        this.personArrayList = personArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_student_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsAdapter.ViewHolder holder, int position) {
        holder.txtFistName.setText(personArrayList.get(position).getEdtfistName());
        holder.txtLastName.setText(personArrayList.get(position).getEdtLastName());
        holder.txtSex.setText(personArrayList.get(position).getRadioButton());
        holder.txtBirthday.setText(personArrayList.get(position).getEdtChoseBirthday());
        holder.txtPhone.setText(personArrayList.get(position).getEdtPhoneNumber());
        holder.txtAddress.setText(personArrayList.get(position).getEdtAddress());
        Picasso.get().load(personArrayList.get(position).getLinkAvt()).into(holder.imgAvt);
    }

    @Override
    public int getItemCount() {
        return (personArrayList == null) ? 0 : personArrayList.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        ImageView imgAvt;
        TextView txtFistName, txtLastName, txtSex, txtBirthday, txtPhone, txtAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAvt = (ImageView) itemView.findViewById(R.id.imgAvatar);
            txtFistName = (TextView) itemView.findViewById(R.id.tvFistName);
            txtLastName = (TextView) itemView.findViewById(R.id.tvLastName);
            txtSex = (TextView) itemView.findViewById(R.id.tvSex);
            txtBirthday = (TextView) itemView.findViewById(R.id.tvBirthday);
            txtPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            txtAddress = (TextView) itemView.findViewById(R.id.tvAddress);
        }
    }

}