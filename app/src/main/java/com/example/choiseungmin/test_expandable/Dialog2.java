package com.example.choiseungmin.test_expandable;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class Dialog2 extends Activity {

    private Button mConfirm, mCancel;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);
        textView = (TextView) findViewById(R.id.textView9);


        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar);
        seekbar.setProgress(10);
        textView.setText(""+seekbar.getProgress()+"%");

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                String seekbarValue = String.valueOf(i);
                textView.setText(""+seekbarValue+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        setContent();
    }

    private void setContent() {
        //mConfirm = (Button) findViewById(R.id.btnConfirm);
        //mCancel = (Button) findViewById(R.id.btnCancel);

        //mConfirm.setOnClickListener(this);
       // mCancel.setOnClickListener(this);
    }

}