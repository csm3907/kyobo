package com.example.choiseungmin.test_expandable;

import android.content.Intent;
import android.os.Bundle;
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

        intent = getIntent();

        url = intent.getStringExtra("url");
        nickname = intent.getStringExtra("nickname");
        email = intent.getStringExtra("email");

        tv_user_id = (TextView) findViewById(R.id.tv_user_id);
        tv_user_name = (TextView) findViewById(R.id.tv_user_name);
        iv_user_profile = (ImageView) findViewById(R.id.iv_user_profile);

        Log.d("result : ",url + nickname + email);
        setLayoutText();

        requestSendMemo();

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
