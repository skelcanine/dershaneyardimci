package com.example.tenter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    String imageId[], whosent[],solved[],imageUrl[];
    Context context;

    public MyAdapter(Context ct, String imageIdx[], String whosentx[], String solvedx[], String imageUrlx[])
    {   context= ct;
        imageId = imageIdx;
        whosent = whosentx;
        solved = solvedx;
        imageUrl = imageUrlx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.idText.setText(imageId[position]);
        holder.sender.setText(whosent[position]);
        //set url to imageView
        //--------------------------------------------
        Picasso.get().load(imageUrl[position]).into(holder.quesImage);
        ////
        if(solved[position].equals("1"))
            holder.solvedImage.setImageResource(R.drawable.checked);
        else holder.solvedImage.setImageResource(R.drawable.unchecked);

        holder.seequestionlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent seeindividualquestion = new Intent(context,individuallQuestion.class);
                seeindividualquestion.putExtra("imageId",imageId[position]);
                seeindividualquestion.putExtra("sender",whosent[position]);
                seeindividualquestion.putExtra("imageUrl",imageUrl[position]);
                context.startActivity(seeindividualquestion);

            }
        });



    }

    @Override
    public int getItemCount() {
        return whosent.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView idText,sender;
        ImageView quesImage,solvedImage;
        ConstraintLayout seequestionlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idText = itemView.findViewById(R.id.imageId);
            sender = itemView.findViewById(R.id.whosent);
            quesImage = itemView.findViewById(R.id.question);
            solvedImage = itemView.findViewById(R.id.solved);
            seequestionlayout = itemView.findViewById(R.id.seeQuestion);
        }
    }
}
