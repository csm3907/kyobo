package com.example.choiseungmin.test_expandable;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.SocialObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class card_demo extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.LayoutManager layoutManager2;
    RecyclerView.Adapter adapter;
    RecyclerView.Adapter adapter2;

    LinearLayout first;
    LinearLayout second;

    GridLayout firstRecyclerView;
    GridLayout secondRecyclerView;

    Context myContext;

    String UID = "203014010594";

    HashMap<Integer,String[]> setdata = new HashMap();
    HashMap<Integer,String[]> setdata2 = new HashMap();

    class MySQL extends AsyncTask<String, Void, String> {

        String queryType;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONObject jObject = null;
            try {
                String data[];
                // PHP에서 받아온 JSON 데이터를 JSON오브젝트로 변환
                String str = new String();
                jObject = new JSONObject(result);
                // results라는 key는 JSON배열로 되어있다.
                JSONArray results = jObject.getJSONArray("data");
                for ( int i = 0; i < results.length(); ++i ) {
                    JSONObject temp = results.getJSONObject(i);
                    if(queryType.compareTo("cust") == 0 ) {
                        if (temp.get("UID").toString().compareTo(UID) == 0) {
                            data = new String[4];
                            data[0] = temp.get("UID").toString();
                            data[1] = temp.get("FUND_NAME").toString();
                            data[2] = temp.get("EARNINGS").toString();
                            data[3] = temp.get("DESCRIPTION").toString();
                            setdata.put(i, data);

                        }

                        first = (LinearLayout) findViewById(R.id.first);
                        second = (LinearLayout) findViewById(R.id.second);
                        firstRecyclerView = (GridLayout) findViewById(R.id.firstRecyclerView);
                        secondRecyclerView = (GridLayout) findViewById(R.id.secondRecyclerView);

                        first.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                if(firstRecyclerView.getVisibility() == View.VISIBLE)
                                    firstRecyclerView.setVisibility(View.GONE);
                                else
                                    firstRecyclerView.setVisibility(View.VISIBLE);
                            }
                        });

                        second.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                if(secondRecyclerView.getVisibility() == View.VISIBLE)
                                    secondRecyclerView.setVisibility(View.GONE);
                                else
                                    secondRecyclerView.setVisibility(View.VISIBLE);
                            }
                        });


                        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                        layoutManager = new LinearLayoutManager(myContext);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new RecyclerAdapter(setdata);
                        recyclerView.setAdapter(adapter);



                    }
                    else
                    {
                        if(temp.get("UID").toString().compareTo(UID) == 0) {
                            data = new String[4];
                            data[0] = temp.get("UID").toString();
                            data[1] = temp.get("FUND_NAME").toString();
                            data[2] = temp.get("EARNINGS").toString();
                            data[3] = temp.get("DESCRIPTION").toString();
                            setdata2.put(i, data);
                        }


                        recyclerView2 = (RecyclerView) findViewById(R.id.recycler_view2);
                        layoutManager2 = new LinearLayoutManager(myContext);
                        recyclerView2.setLayoutManager(layoutManager2);
                        adapter2 = new RecyclerAdapter(setdata2);
                        recyclerView2.setAdapter(adapter2);

                    }
                }








            } catch (JSONException e) {
                e.printStackTrace();
            }
            // mTextViewResult.setText(result);
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = (String)params[1];
            String query = (String)params[6];
            String type = (String)params[0];
            String db_ip = (String)params[2];
            String db_id = (String)params[3];
            String db_password = (String)params[4];
            String db_dbName = (String)params[5];
            queryType= (String)params[7];

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
        setContentView(R.layout.activity_card_demo);
        // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);

        myContext = this;

                String data[] = new String[4];
                data[0] = "fffff";
                data[1] = "fffff";
                data[2] = "fffff";
                data[3] = "fffff";
                setdata.put(0, data);


            first = (LinearLayout) findViewById(R.id.first);
            second = (LinearLayout) findViewById(R.id.second);
            firstRecyclerView = (GridLayout) findViewById(R.id.firstRecyclerView);
            secondRecyclerView = (GridLayout) findViewById(R.id.secondRecyclerView);

            first.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(firstRecyclerView.getVisibility() == View.VISIBLE)
                        firstRecyclerView.setVisibility(View.GONE);
                    else
                        firstRecyclerView.setVisibility(View.VISIBLE);
                }
            });

            second.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if(secondRecyclerView.getVisibility() == View.VISIBLE)
                        secondRecyclerView.setVisibility(View.GONE);
                    else
                        secondRecyclerView.setVisibility(View.VISIBLE);
                }
            });


            recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            layoutManager = new LinearLayoutManager(myContext);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerAdapter(setdata);
            recyclerView.setAdapter(adapter);



            // 디비에서 정보 get
       // MySQL task = new MySQL();   //"insert into users(id,email,password) values('7','fuck','fuck')" // "select * from users"
        //task.execute("http://192.168.0.3/~koo/android.php", "localhost:3306","root","password","test2","insert into users(id,email,password) values('9','쿠영서','fuck')");
       // task.execute("list","http://172.30.70.42/~koo/android.php", "172.30.70.42:3306","root","password","test2","select * from cust_fund", "cust");
       // MySQL task2 = new MySQL();

        //task2.execute("list","http://172.30.70.42/~koo/android.php", "172.30.70.42:3306","root","password","test2","select * from recommend_fund", "recommend");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
