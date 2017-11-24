package com.example.choiseungmin.test_expandable;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.kakaotalk.KakaoTalkService;
import com.kakao.kakaotalk.callback.TalkResponseCallback;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.FeedTemplate;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.SocialObject;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;

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

public class AgreeActivity extends AppCompatActivity {


    FeedTemplate params;
    Button button;
    String str;

    class MySQL extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(AgreeActivity.this,
                    "Please Wait", null, true, true);
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
                    zz += temp.toString();
                    zz += '\n';
                    str += zz;
                }

                params = FeedTemplate
                        .newBuilder(ContentObject.newBuilder("변액보험 추천",
                                "http://pds.joins.com/news/component/htmlphoto_mmdata/201703/07/b29ce0e1-795f-48d2-8f58-cdcc4039a26b.jpg",
                                LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                                        .setMobileWebUrl("https://developers.kakao.com").build())
                                .setDescrption(str) //"고객 맞춤 추천 서비스입니다"
                                .build())
                        .setSocial(SocialObject.newBuilder().setLikeCount(10).setCommentCount(20)
                                .setSharedCount(30).setViewCount(40).build())
                        //.addButton(new ButtonObject("웹에서 보기", LinkObject.newBuilder().setWebUrl("'https://developers.kakao.com").setMobileWebUrl("'https://developers.kakao.com").build()))
                        .addButton(new ButtonObject("장바구니 담기", LinkObject.newBuilder()
                                .setWebUrl("'https://developers.kakao.com")
                                .setMobileWebUrl("'https://developers.kakao.com")
                                .setAndroidExecutionParams("key1=value1")
                                .setIosExecutionParams("key1=value1")
                                .build()))
                        .build();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            progressDialog.dismiss();
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

    //Database에 사용자가 동의를 하였음을 의미한다
    public void setDatabaseAgreement(){
       // MySQL task = new MySQL();   //"insert into users(id,email,password) values('7','fuck','fuck')" // "select * from users"
       // //task.execute("http://192.168.0.3/~koo/android.php", "localhost:3306","root","password","test2","insert into users(id,email,password) values('9','쿠영서','fuck')");
       // task.execute("http://192.168.0.3/~koo/android.php", "192.168.0.3:3306","root","password","test2","select * from users");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agree);
        button = findViewById(R.id.button);

        //MySQL task = new MySQL();   //"insert into users(id,email,password) values('7','fuck','fuck')" // "select * from users"
        //task.execute("http://192.168.0.3/~koo/android.php", "localhost:3306","root","password","test2","insert into users(id,email,password) values('9','쿠영서','fuck')");
        //task.execute("http://192.168.0.3/~koo/android.php", "192.168.0.3:3306","root","password","test2","select * from users");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDatabaseAgreement();
                requestSendMemo();
                /*
                KakaoLinkService.getInstance().sendDefault(getApplicationContext(), params, new ResponseCallback<KakaoLinkResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Logger.e(errorResult.toString());
                    }

                    @Override
                    public void onSuccess(KakaoLinkResponse result) {

                    }
                });
                */
            }
        });
    }

    public void requestSendMemo() {
        String message = "Test for send Memo";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("''yy년 MM월 dd일 E요일");
        KakaoTalkMessageBuilder builder = new KakaoTalkMessageBuilder();
        builder.addParam("MESSAGE", message);
        builder.addParam("DATE", sdf.format(date));

        KakaoTalkService.requestSendMemo(new KakaoTalkResponseCallback<Boolean>() {
                                             @Override
                                             public void onSuccess(Boolean result) {
                                                 Logger.d("send message to my chatroom : " + result);
                                             }
                                         }
                , "6716" // templateId
                , builder.build());
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
            //redirectLoginActivity();
        }

        @Override
        public void onNotSignedUp() {
            //redirectSignupActivity();
        }
    }

}
