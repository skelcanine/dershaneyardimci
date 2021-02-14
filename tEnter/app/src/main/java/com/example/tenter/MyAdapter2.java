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

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    String answerId[], whosent[],imageId[],imageUrl[];
    Context context;

    public MyAdapter2(Context ct, String answerIdx[], String whosentx[], String imageIdx[], String imageUrlx[])
    {   context= ct;
        answerId = answerIdx;
        whosent = whosentx;
        imageId = imageIdx;
        imageUrl = imageUrlx;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.myrow2,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.questionId.setText("Soru: "+imageId[position]);
        holder.answerId.setText("Cevap: "+(position+1));
        holder.senderId.setText("Gonderen: "+whosent[position]);

        Picasso.get().load(imageUrl[position]).into(holder.quesImage);

        holder.seeanswerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent seeindividualanswer = new Intent(context,seeindividualanswer.class);
                seeindividualanswer.putExtra("imageId",imageId[position]);
                seeindividualanswer.putExtra("senderId",whosent[position]);
                seeindividualanswer.putExtra("imageUrl",imageUrl[position]);
                String positionx= String.valueOf( position+1);
                seeindividualanswer.putExtra("answerId",positionx);
                context.startActivity(seeindividualanswer);



            }
        });

    }

    @Override
    public int getItemCount() {
        return whosent.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView questionId,senderId,answerId;
        ImageView quesImage;
        ConstraintLayout seeanswerlayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            questionId = itemView.findViewById(R.id.questionId);
            senderId = itemView.findViewById(R.id.whosent);
            quesImage = itemView.findViewById(R.id.question);
            answerId = itemView.findViewById(R.id.answerId);
            seeanswerlayout = itemView.findViewById(R.id.seeAnswer);
        }
    }
}
