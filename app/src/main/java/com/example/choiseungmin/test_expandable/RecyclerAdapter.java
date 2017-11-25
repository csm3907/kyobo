package com.example.choiseungmin.test_expandable;

/**
 * Created by choiseungmin on 2017. 11. 23..
 */
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    HashMap<Integer,String[]> List;
    String data[] = new String[3];


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
        viewHolder.Extra.setText(data[3]);
        viewHolder.Title.setText(data[1]);
        viewHolder.Contents.setText(data[0] +":::"+ data[2]);
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Log.d("popupButton " + i, "shouldPopupShow");
                /*
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        view.getContext());

                builder.setTitle("AlertDialog Title");
                builder.setMessage("AlertDialog Content");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(view.getContext(),"예를 선택했습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(view.getContext(),"아니오를 선택했습니다.",Toast.LENGTH_LONG).show();
                            }
                        });
                builder.show();
                */

                
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Title...");


                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}

