package com.example.choiseungmin.test_expandable;

/**
 * Created by choiseungmin on 2017. 11. 23..
 */
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    String type;
    HashMap<Integer,String[]> List;
    String data[] = new String[3];


    RecyclerAdapter(String str, HashMap<Integer,String[]> data)
    {
        type = str;
        List = data;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView Title;
        public TextView Contents;
        public TextView Extra;

        public MyViewHolder(View itemView) {
            super(itemView);
            //itemImage = (ImageView)itemView.findViewById(R.id.UserImage);
            Title = (TextView)itemView.findViewById(R.id.TitleText);
            Contents = (TextView)itemView.findViewById(R.id.contentsText);
            Extra = (TextView)itemView.findViewById(R.id.ExtraText);

            /*
            itemDetail =
                    (TextView)itemView.findViewById(R.id.item_subhead);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
            */
        }

    }
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_layout, viewGroup, false);
        MyViewHolder viewHolder1 = new MyViewHolder(v);
        return viewHolder1;
    }


    @Override
    public void onBindViewHolder(RecyclerAdapter.MyViewHolder viewHolder, int i) {
        //viewHolder.itemTitle.setText(titles[i]);
        //viewHolder.itemDetail.setText(details[i]);
        //viewHolder.itemImage.setImageResource(images[i]);
        Log.v("TAG",""+i);
        if(type.compareTo("fuck1") == 0){

            viewHolder.Extra.setText("fuck");
            data = List.get(i);
            viewHolder.Title.setText(data[1]);
            viewHolder.Contents.setText(data[0] + data[2]);

        }
        else{
            viewHolder.Extra.setText("fuck1");
            data = List.get(i);
            viewHolder.Title.setText(data[1]);
            viewHolder.Contents.setText(data[0] + data[2]);
        }



    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}

