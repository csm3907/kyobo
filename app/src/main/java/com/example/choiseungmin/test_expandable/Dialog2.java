package com.example.choiseungmin.test_expandable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Dialog2 extends Activity {

    private TextView textView;
    private String seekbarValue;
    private String percnet ;
    private String id;
    private String fund_name;
    private String description;
    Context myContext ;
    String type;
    int remain = 100 - RecyclerAdapter.max;


    class MySQL extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(type.compareTo("cust") == 0) {
                Intent intent = new Intent(myContext,card_demo.class);
                myContext.startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            else{
                type = "cust";
                new MySQL().execute("http://172.30.70.42/~koo/android.php", "172.30.70.42:3306", "root", "password", "test2", "DELETE FROM RECOMMEND_FUND  WHERE UID = '"+id+"' AND FUND = '"+fund_name+"';");

            }


        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = (String)params[0];
            String query = (String)params[5];
            String db_ip = (String)params[1];
            String db_id = (String)params[2];
            String db_password = (String)params[3];
            String db_dbName = (String)params[4];
            String postParameters = "query=" + query + "&ip=" + db_ip+ "&id=" + db_id+ "&password=" + db_password+ "&db=" + db_dbName; // post 전송
            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                // input
                int responseStatusCode = httpURLConnection.getResponseCode();
                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }
                bufferedReader.close();
                return sb.toString();


            } catch (Exception e) {
                return new String("Error: " + e.getMessage());
            }

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog);

        myContext = this;

        textView = (TextView) findViewById(R.id.textView9);


        Intent intent = getIntent();
        percnet = intent.getStringExtra("percnet");
        id = intent.getStringExtra("uid");
        fund_name = intent.getStringExtra("fund_name");
        type = intent.getStringExtra("type");
        description = intent.getStringExtra("description");

        textView = (TextView) findViewById(R.id.textView9);
        SeekBar seekbar = (SeekBar) findViewById(R.id.seekBar);

        if(remain + Integer.parseInt(percnet) >= 100)
            seekbar.setMax(100);
        else
            seekbar.setMax(remain + Integer.parseInt(percnet));


        seekbar.setProgress(Integer.parseInt(percnet));
        textView.setText(""+seekbar.getProgress()+"%");
        seekbarValue = ""+ seekbar.getProgress();

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                seekbarValue = String.valueOf(i);
                textView.setText(""+seekbarValue+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        Button button = (Button)findViewById(R.id.dialogButtonOK);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerAdapter.max = 0;

                if(seekbarValue.compareTo("0") != 0) {
                    if (type.compareTo("cust") == 0) {
                        MySQL task = new MySQL();   //"insert into users(id,email,password) values('7','fuck','fuck')" // "select * from users"
                        task.execute("http://172.30.70.42/~koo/android.php", "172.30.70.42:3306", "root", "password", "test2", "UPDATE CUST_FUND SET RATIO = '" + seekbarValue + "' WHERE UID = '" + id + "' AND FUND_NAME = '" + fund_name + "'");
                    } else {

                        Log.v("TAG", "" + "DELETE FROM RECOMMEND_FUND  WHERE UID = '" + id + "' AND FUND = '" + fund_name + "'");
                        MySQL task = new MySQL();   //"insert into users(id,email,password) values('7','fuck','fuck')" // "select * from users"
                        task.execute("http://172.30.70.42/~koo/android.php", "172.30.70.42:3306", "root", "password", "test2", "insert into cust_fund(UID,FUND_NAME,EARNINGS,RATIO,DESCRIPTION) values('" + id + "','" + fund_name + "','" + 0 + "','" + seekbarValue + "','" + description + "')");
                    }
                }
                else{
                    Intent intent = new Intent(myContext,card_demo.class);
                    myContext.startActivity(intent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            }
        });


        //
    }

    private void setContent() {
        //mConfirm = (Button) findViewById(R.id.btnConfirm);
        //mCancel = (Button) findViewById(R.id.btnCancel);

        //mConfirm.setOnClickListener(this);
        // mCancel.setOnClickListener(this);
    }

}