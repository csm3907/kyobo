package com.example.choiseungmin.test_expandable;

/**
 * Created by choiseungmin on 2017. 11. 23..
 */
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    HashMap<Integer,String[]> List;
    String data[] = new String[3];
    public SeekBar seekBar;

    RecyclerAdapter(HashMap<Integer,String[]> data)
    {
        List = data;
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView Title;
        public TextView Contents;
        public TextView Extra;
        public Button button;


        public MyViewHolder(final View itemView) {
            super(itemView);
            //itemImage = (ImageView)itemView.findViewById(R.id.UserImage);
            Title = (TextView)itemView.findViewById(R.id.TitleText);
            Contents = (TextView)itemView.findViewById(R.id.contentsText);
            Extra = (TextView)itemView.findViewById(R.id.ExtraText);
            button = (Button)itemView.findViewById(R.id.buttonPopup);
            seekBar = (SeekBar)itemView.findViewById(R.id.seekBar);
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
    public void onBindViewHolder(final RecyclerAdapter.MyViewHolder viewHolder, final int i) {
        //viewHolder.itemTitle.setText(titles[i]);
        //viewHolder.itemDetail.setText(details[i]);
        //viewHolder.itemImage.setImageResource(images[i]);

        data = List.get(i);
        viewHolder.Extra.setText(data[4]);
        viewHolder.Title.setText(data[1]);
        viewHolder.Contents.setText(data[0] +":::"+ data[2]+":::"+data[3]);
        View.OnClickListener listener = new MyListener(data[3],i);
        viewHolder.button.setOnClickListener(listener);
    }

    class MyListener implements View.OnClickListener {
        String data;
        int i;
        MyListener(String data, int i){
            this.data = data;
            this.i = i;
        }
        @Override
        public void onClick(final View view) {

            Intent intent = new Intent(view.getContext(),Dialog2.class);
            intent.putExtra("percnet",data);
            intent.putExtra("order",i);
            view.getContext().startActivity(intent);

        }
    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}