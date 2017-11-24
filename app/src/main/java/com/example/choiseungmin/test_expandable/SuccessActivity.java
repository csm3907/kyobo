package com.example.choiseungmin.test_expandable;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kakao.kakaotalk.KakaoTalkService;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.network.ErrorResult;
import com.kakao.util.helper.log.Logger;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SuccessActivity extends AppCompatActivity {

    private String url;
    private String nickname;
    private String email;

    // view
    private Button login_button;
    private TextView tv_user_id;
    private TextView tv_user_name;
    private ImageView iv_user_profile;

    private String userName;
    private String userId;
    private String profileUrl;

    Intent intent;
    Handler mHandler;

    class BackGround extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            JSONObject jObject = null;
            try {
                // PHP에서 받아온 JSON 데이터를 JSON오브젝트로 변환
                jObject = new JSONObject(result);
                // results라는 key는 JSON배열로 되어있다.
                JSONArray results = jObject.getJSONArray("data");
                String zz = "";
                for ( int i = 0; i < results.length(); ++i ) {
                    JSONObject temp = results.getJSONObject(i);

                    if(temp.get("SEND_STATUS").toString().compareTo("Y") == 0){
                        requestSendMemo();
                        new BackGround().execute("http://192.168.0.3/~koo/android.php", "192.168.0.3:3306","root","password","test2","UPDATE SEND_MESSAGE SET SEND_STATUS = 'N' WHERE UID = '"+temp.get("UID").toString()+"' AND FUND_NAME = '"+temp.get("FUND_NAME").toString()+"'");
                    }

                }

                mHandler.postDelayed(new Runnable() {public void run() {new BackGround().execute("http://192.168.0.3/~koo/android.php", "192.168.0.3:3306","root","password","test2","select * from SEND_MESSAGE");}}, 5000);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            // mTextViewResult.setText(result);
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

    private void redirectLoginActivity() {
        Log.d("TAG ","LoginActivity");
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void redirectSignupActivity() {
        final Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private abstract class KakaoTalkResponseCallback<T> extends TalkResponseCallback<T> {
        @Override
        public void onNotKakaoTalkUser() {
            Logger.w("not a KakaoTalk user");
        }

        @Override
        public void onFailure(ErrorResult errorResult) {
            Logger.e("failure : " + errorResult);
        }

        @Override
        public void onSessionClosed(ErrorResult errorResult) {
            Logger.e("failure222 : " + errorResult);
            //redirectLoginActivity();
        }

        @Override
        public void onNotSignedUp() {
            //redirectSignupActivity();
        }
    }


    public void requestSendMemo() {

        String message = "Test for send Memo";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("''yy년 MM월 dd일 E요일");
        SuccessActivity.KakaoTalkMessageBuilder builder = new SuccessActivity.KakaoTalkMessageBuilder();
        builder.addParam("MESSAGE", message);
        builder.addParam("DATE", sdf.format(date));

        KakaoTalkService.requestSendMemo(new KakaoTalkResponseCallback<Boolean>() {
                                             @Override
                                             public void onSuccess(Boolean result) {
                                                 Logger.d("send message to my chatroom : " + result);
                                             }
                                         }
                , "6726" // templateId
                , builder.build());
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
    public class KakaoTalkMessageBuilder {
        public Map<String, String> messageParams = new HashMap<String, String>();

        public KakaoTalkMessageBuilder addParam(String key, String value) {
            messageParams.put("${" + key + "}", value);
            return this;
        }

        public Map<String, String> build() {
            return messageParams;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {public void run() {new BackGround().execute("http://192.168.0.3/~koo/android.php", "192.168.0.3:3306","root","password","test2","select * from SEND_MESSAGE");}}, 5000);


        intent = getIntent();

        url = intent.getStringExtra("url");
        nickname = intent.getStringExtra("nickname");
        email = intent.getStringExtra("email");

        tv_user_id = (TextView) findViewById(R.id.tv_user_id);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        iv_user_profile = (ImageView) findViewById(R.id.iv_user_profile);

        Log.d("result : ",url + nickname + email);
        setLayoutText();



    }

    private void setLayoutText(){
        tv_user_id.setText(email);
        tv_user_name.setText(nickname);

        Picasso.with(this)
                .load(url)
                .fit()
                .into(iv_user_profile);
    }
}
