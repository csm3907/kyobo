package com.example.choiseungmin.test_expandable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
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

import com.kakao.auth.ErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
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
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
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
    SessionCallback callback;
    Intent Successintent;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //간편로그인시 호출 ,없으면 간편로그인시 로그인 성공화면으로 넘어가지 않음
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {

            UserManagement.requestMe(new MeResponseCallback() {

                @Override
                public void onFailure(ErrorResult errorResult) {
                    String message = "failed to get user info. msg=" + errorResult;
                    Logger.d(message);

                    ErrorCode result = ErrorCode.valueOf(errorResult.getErrorCode());
                    if (result == ErrorCode.CLIENT_ERROR_CODE) {
                        //finish();
                    } else {
                        //redirectMainActivity();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    finish();
                }

                @Override
                public void onNotSignedUp() {
                }

                @Override
                public void onSuccess(UserProfile userProfile) {
                    //로그인에 성공하면 로그인한 사용자의 일련번호, 닉네임, 이미지url등을 리턴합니다.
                    //사용자 ID는 보안상의 문제로 제공하지 않고 일련번호는 제공합니다.


                    Successintent = new Intent(AgreeActivity.this, SuccessActivity.class);
                    Successintent = Successintent.putExtra("nickname", userProfile.getNickname());

                    if(userProfile.getEmail() == null){
                        Successintent.putExtra("email","no email");
                    }else{
                        Successintent.putExtra("email",userProfile.getEmail());
                    }

                    if(userProfile.getProfileImagePath() == null){
                        Successintent.putExtra("url","https://www.google.co.kr/url?sa=i&rct=j&q=&esrc=s&source=images&cd=&cad=rja&uact=8&ved=0ahUKEwj0zKOk0MbXAhVCFpQKHYTkDO8QjRwIBw&url=http%3A%2F%2Fnews.sbs.co.kr%2Fnews%2FendPage.do%3Fnews_id%3DN1004259221&psig=AOvVaw1l1ZkA3q4zo3hYdqRkIxDY&ust=1511043083837874");
                    }else{
                        Successintent.putExtra("url",userProfile.getProfileImagePath());
                    }


                    Log.e("UserProfile", userProfile.toString());
                    Session.getCurrentSession().removeCallback(callback);
                    startActivity(Successintent);
                    finish();
                }
            });


        }
        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            // 세션 연결이 실패했을때
            // 어쩔때 실패되는지는 테스트를 안해보았음 ㅜㅜ
            Log.d("SessionFailed ","session is failed");
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

        //button = findViewById(R.id.button);

        //MySQL task = new MySQL();   //"insert into users(id,email,password) values('7','fuck','fuck')" // "select * from users"
        //task.execute("http://192.168.0.3/~koo/android.php", "localhost:3306","root","password","test2","insert into users(id,email,password) values('9','쿠영서','fuck')");
        //task.execute("http://192.168.0.3/~koo/android.php", "192.168.0.3:3306","root","password","test2","select * from users");


        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();

        /**카카오톡 로그아웃 요청**/
        //한번 로그인이 성공하면 세션 정보가 남아있어서 로그인창이 뜨지 않고 바로 onSuccess()메서드를 호출합니다.
        //테스트 하시기 편하라고 매번 로그아웃 요청을 수행하도록 코드를 넣었습니다 ^^
        /*
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
                //로그아웃 성공 후 하고싶은 내용 코딩 ~
            }
        });
        */



        /*
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setDatabaseAgreement();
                //requestSendMemo();

                KakaoLinkService.getInstance().sendDefault(getApplicationContext(), params, new ResponseCallback<KakaoLinkResponse>() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        Logger.e(errorResult.toString());
                    }

                    @Override
                    public void onSuccess(KakaoLinkResponse result) {

                    }
                });

            }
        });
        */
    }



}
