package com.siv.reqresexample;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class ReqResAdapter extends RecyclerView.Adapter<ReqResAdapter.ReqResViewHolder> {

    Context context;
    ArrayList<ReqresModel> data;

    public ReqResAdapter(Context context, ArrayList<ReqresModel> data) {
        this.context = context;
        this.data = data;

    }

    @NonNull
    @Override
    public ReqResAdapter.ReqResViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);

        return new ReqResViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReqResAdapter.ReqResViewHolder holder, int position) {

        holder.firstName.setText(data.get(position).getFirst_name());
        Log.d("TAG", "onBindViewHolder: FirstName : " + data.get(position).getFirst_name() +
                "\n" + data.get(position).getLast_name());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ReqResViewHolder extends RecyclerView.ViewHolder {

        TextView firstName, lastName, email;
        CircularImageView avatar_image;

        public ReqResViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName_text);
            lastName = itemView.findViewById(R.id.lastName_text);
            email = itemView.findViewById(R.id.email_text);


        }
    }
}
