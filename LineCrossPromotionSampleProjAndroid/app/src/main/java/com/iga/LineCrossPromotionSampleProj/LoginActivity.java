package com.iga.LineCrossPromotionSampleProj;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
public class LoginActivity extends Activity {

    private Button loginBtn;
    private EditText lineAppId, lineUserKey;
    private final String LOG_TAG = "LINE_BVT";
    private final String GET_ENCRYPTED_USER_KEY = "http://game-api-staging.line-apps.com/achievement/v3.0/mock/encryption";
 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);

        makeUiComponent();

        
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getEncryptedLineUserKey();

            }
        });

        Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                String userKey = bundle.getString("userKey");
                int resultCode = bundle.getInt("resultCode");


            }
        };



 }

    private void getEncryptedLineUserKey() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = "";
                String plainUserKey = lineUserKey.getText().toString();
                String appId = lineAppId.getText().toString();

                HttpURLConnection conn = null;

                OutputStream outputStream = null;
                InputStream inputStream = null;
                ByteArrayOutputStream byteArrayOutputStream = null;
                URL url = null;


                try {
                    url = new URL(GET_ENCRYPTED_USER_KEY);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(2000);
                    conn.setReadTimeout(3000);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("X-Linegame-AppId", lineAppId.getText().toString());

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("userKey", plainUserKey);
                    jsonObject.put("appId", appId);

                    outputStream = conn.getOutputStream();
                    outputStream.write(jsonObject.toString().getBytes());
                    outputStream.flush();


                    int responseCode = conn.getResponseCode();
                    if(responseCode == HttpURLConnection.HTTP_OK){
                        inputStream = conn.getInputStream();
                        byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] byteBuffer = new byte[1024];
                        byte[] byteData = null;
                        int length = 0;
                        while((length = inputStream.read(byteBuffer, 0, byteBuffer.length)) != -1) {
                            byteArrayOutputStream.write(byteBuffer, 0, length);
                        }
                        byteData = byteArrayOutputStream.toByteArray();

                        response = new String(byteData);

                        JSONObject responseJSON = new JSONObject(response);
                        int resultCode = (int) responseJSON.get("resultCode");
                        String encryptedUserKey = (String) responseJSON.get("resultMessage");

                        Log.i(LOG_TAG, "DATA response = " + resultCode);

                        if(resultCode == 0){
//                            Toast.makeText(LoginActivity.this, "Login Succeed :: " + encryptedUserKey, Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(LoginActivity.this, BridgeActivity.class);
                            intent.putExtra("UserKey", encryptedUserKey);
                            intent.putExtra("AppId", appId);

                            startActivity(intent);
                        }

                    }else{
//                        Toast.makeText(LoginActivity.this, "Fail to get encrypted Line user key", Toast.LENGTH_SHORT).show();
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


        /*
        * curl -X POST -H "Content-Type: application/json"
        * -H "X-Linegame-AppId: TEST_CP_TANTAN"
        * -d         * '{"userKey":"1T4qgG1tAGzky9Gb/pKkFdw==", "appId":"TEST_CP_TANTAN"}'
        * "http://game-api-staging.line-apps.com/achievement/v3.0/mock/encryption"
        *
        *
        * */
    }




    public void makeUiComponent() {
        loginBtn = (Button) findViewById(R.id.loginBtn);
        lineAppId = (EditText) findViewById(R.id.lineAppId);
        lineUserKey = (EditText) findViewById(R.id.lineUserKey);
    }


}
