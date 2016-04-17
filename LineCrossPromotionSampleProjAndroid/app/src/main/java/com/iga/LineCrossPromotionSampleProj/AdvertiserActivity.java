package com.iga.LineCrossPromotionSampleProj;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.line.crosspromotion.LineCrossPromotion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by MikeHan on 2016-04-16.
 */
public class AdvertiserActivity extends Activity {

    private Button signInBtn, signUpBtn;

    private String appId, appSecret, actionId, userKey;
    private String LOG_TAG = "LINE_BVT";
    private final String ENCREMENT_URL_FORMAT = "http://10.113.191.80/achievement/v3.0/actions/{actionId}/increment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertiser_layout);

        makeUiComponents();

        Bundle bundle = getIntent().getExtras();
        appId = bundle.getString("AppId");
        userKey = bundle.getString("UserKey");

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineCrossPromotion.unlock(LineCrossPromotion.Event.SIGN_IN);
                Toast.makeText(AdvertiserActivity.this, "Unlock Call :: Sign In!", Toast.LENGTH_SHORT).show();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineCrossPromotion.unlock(LineCrossPromotion.Event.SIGN_UP);
                Toast.makeText(AdvertiserActivity.this, "Unlock Call :: Sign Up!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    // CALL startSession api
    @Override
    protected void onResume() {
        super.onResume();
        LineCrossPromotion.startSession(AdvertiserActivity.this);
    }

    // CALL endSession api
    @Override
    protected void onPause() {
        super.onPause();
        LineCrossPromotion.endSession();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void makeUiComponents() {
        signInBtn = (Button)findViewById(R.id.signInBtnOnAdvertiser);
        signUpBtn = (Button)findViewById(R.id.signUpBtnOnAdvertiser);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setHomeAsUpIndicator(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeEncrementCall(String lineActionId) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                HttpURLConnection conn;
                OutputStream outputStream;
                InputStream inputStream;
                ByteArrayOutputStream byteArrayOutputStream;

                String response = "";
                String ENCREMENT_URL = ENCREMENT_URL_FORMAT.replace("{actionId}", actionId);

                try {
                    URL url = new URL(ENCREMENT_URL);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(2000);
                    conn.setReadTimeout(3000);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("X-Linegame-AppId", appId);
                    conn.setRequestProperty("X-Linegame-AppSecret", appSecret);

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userKey", userKey);
                    jsonObject.put("point", 10);

                    outputStream = conn.getOutputStream();
                    outputStream.write(jsonObject.toString().getBytes());
                    outputStream.flush();


                    int responseCode = conn.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        inputStream = conn.getInputStream();
                        byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] byteBuffer = new byte[1024];

                        int length = 0;
                        while((length = inputStream.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                            byteArrayOutputStream.write(byteBuffer, 0, length);
                        }
                        byte[] byteData = byteArrayOutputStream.toByteArray();

                        response = new String(byteData);

                        JSONObject responseJSON = new JSONObject(response);
                        int resultCode = (int) responseJSON.get("resultCode");
                        String encryptedUserKey = (String) responseJSON.get("resultMessage");

                        if(resultCode == 0){

                        }else{
                            Log.d(LOG_TAG, "ERROR CODE : " + resultCode);
                        }

                    }else{
                        Log.d(LOG_TAG, "HTTP ERROR CODE : " + responseCode);
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
}
