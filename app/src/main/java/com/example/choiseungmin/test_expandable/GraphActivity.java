package com.example.choiseungmin.test_expandable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class GraphActivity extends Activity {

    class MySQL extends AsyncTask<String, Void, String> {

        ArrayList<DataPoint> kospi;
        ArrayList<DataPoint> kospi200;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONObject jObject = null;
            try {
                kospi = new ArrayList<DataPoint>();
                kospi200 = new ArrayList<DataPoint>();
                String data[];
                // PHP에서 받아온 JSON 데이터를 JSON오브젝트로 변환
                String str = new String();
                jObject = new JSONObject(result);
                // results라는 key는 JSON배열로 되어있다.
                JSONArray results = jObject.getJSONArray("data");
                for ( int i = 0; i < results.length(); ++i ) {
                    JSONObject temp = results.getJSONObject(i);
                    kospi.add(new DataPoint(i+1, Float.valueOf(temp.get("KOSPI").toString())));
                    kospi200.add(new DataPoint(i+1, Float.valueOf(temp.get("KOSPI200").toString())));

                }

                DataPoint[] datapoint = new DataPoint[kospi.size()];
                datapoint = kospi.toArray(datapoint);

                //Log.v("Data ",""+datapoint);

                DataPoint[] datapoint2 = new DataPoint[kospi200.size()];
                datapoint2 = kospi200.toArray(datapoint2);

                GraphView graph = (GraphView) findViewById(R.id.graph);
                LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(datapoint);
                graph.addSeries(series);
/*
                LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(datapoint2);
                Paint paint =  new Paint();
                paint.setColor(Color.RED);
                paint.setStrokeWidth(5);
                series2.setCustomPaint(paint);
*/
                //series2.setThickness(150);

                //graph.addSeries(series2);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        protected String doInBackground(String...  params) {

            String serverURL = (String)params[1];
            String query = (String)params[6];
            String type = (String)params[0];
            String db_ip = (String)params[2];
            String db_id = (String)params[3];
            String db_password = (String)params[4];
            String db_dbName = (String)params[5];

            String postParameters = "query=" + query + "&type=" + type +"&ip=" + db_ip+ "&id=" + db_id+ "&password=" + db_password+ "&db=" + db_dbName; // post 전송
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_graph);

        MySQL task = new MySQL();   //"insert into users(id,email,password) values('7','fuck','fuck')" // "select * from users"
        task.execute("kospi","http://172.30.70.42/~koo/android.php", "172.30.70.42:3306","root","password","test2","SELECT * FROM KOSPI_KOSPI200 WHERE DATE BETWEEN '2017-07-01' AND '2017-11-17'");


    }
    @Override
    public void onBackPressed(){
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        super.onBackPressed();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return false;
    }
}
