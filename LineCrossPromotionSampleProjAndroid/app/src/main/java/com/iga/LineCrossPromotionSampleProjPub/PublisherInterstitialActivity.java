package com.iga.LineCrossPromotionSampleProjPub;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.igaworks.adbrix.line.InitInterstitialAdEventListener;
import com.igaworks.adbrix.line.ShowInterstitialAdEventListener;
import com.line.crosspromotion.LineCrossPromotion;

/**
 * Created by MikeHan on 2016-04-16.
 */
public class PublisherInterstitialActivity extends Activity {

    Button loginBtn, shopOutBtn, appEndBtn, showInterstitialBtn;
    EditText customeAdSpaceKey;
    private String appId, userKey, encryptedUserKey;
    private String LOGIN_ADSPACEKEY = "";
    private String SHOP_OUT_ADSPACEKEY = "";
    private String APP_END_ADSPACEKEY = "";

    private String LOG_TAG = "LINE_BVT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_interstitial_layout);

        makeUiComponents();

        Bundle bundle = getIntent().getExtras();
        appId = bundle.getString("AppId");
        userKey = bundle.getString("UserKey");
        encryptedUserKey = bundle.getString("EncryptedUserKey");

        LineCrossPromotion.setUserId(encryptedUserKey);

        LineCrossPromotion.initInterstitialAd(PublisherInterstitialActivity.this, new InitInterstitialAdEventListener() {
            @Override
            public void OnInitSuccess() {
                Toast.makeText(PublisherInterstitialActivity.this,
                        "Init InterstitialAd is succeeded!!",
                        Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void OnInitFail(String errorMsg) {
                Toast.makeText(PublisherInterstitialActivity.this,
                        "Init InterstitialAd is failed!!" +
                        "\nerrorMessage : " + errorMsg,
                        Toast.LENGTH_LONG)
                        .show();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PublisherInterstitialActivity.this, "Call InterstitialAd with LogIn AdSpaceKey", Toast.LENGTH_LONG).show();
                LineCrossPromotion.showInterstitialAd(PublisherInterstitialActivity.this, LOGIN_ADSPACEKEY, registerListener());
            }
        });

        shopOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PublisherInterstitialActivity.this, "Call InterstitialAd with ShopOut AdSpaceKey", Toast.LENGTH_LONG).show();
                LineCrossPromotion.showInterstitialAd(
                        PublisherInterstitialActivity.this,
                        SHOP_OUT_ADSPACEKEY,
                        registerListener()
                );
            }
        });

        appEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PublisherInterstitialActivity.this, "Call InterstitialAd with AppEnd AdSpaceKey", Toast.LENGTH_LONG).show();
                LineCrossPromotion.showInterstitialAd(
                        PublisherInterstitialActivity.this,
                        APP_END_ADSPACEKEY,
                        registerListener()
                );
            }
        });

        showInterstitialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PublisherInterstitialActivity.this, "Call InterstitialAd with Custom AdSpaceKey", Toast.LENGTH_LONG).show();
                LineCrossPromotion.showInterstitialAd(
                        PublisherInterstitialActivity.this,
                        LOGIN_ADSPACEKEY,
                        registerListener()
                );
            }
        });
    }

    private ShowInterstitialAdEventListener registerListener(){

        ShowInterstitialAdEventListener listener = new ShowInterstitialAdEventListener() {
            @Override
            public void OnShowInterstitialSuccess(String adspacekey) {
                Log.d(LOG_TAG, "OnShowInterstitialSuccess\nadspacekey : " + adspacekey);
            }

            @Override
            public void OnShowInterstitialFailure(String adspacekey, String errorMsg) {
                Log.d(LOG_TAG, "OnShowInterstitialFailure" +
                        "\nadspacekey : " + adspacekey +
                        "\nerrorMessage : " + errorMsg);
            }

            @Override
            public void OnCloseInterstitial(String adspacekey) {
                Log.d(LOG_TAG, "OnCloseInterstitial" +
                        "\nadspacekey : " + adspacekey);
            }
        };

        return listener;
    }

    // CALL startSession api
    @Override
    protected void onResume() {
        super.onResume();
        LineCrossPromotion.startSession(PublisherInterstitialActivity.this);
    }

    // CALL endSession api
    @Override
    protected void onPause() {
        super.onPause();
        LineCrossPromotion.endSession();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void makeUiComponents() {
        loginBtn = (Button)findViewById(R.id.loginBtnOnPubInter);
        shopOutBtn = (Button)findViewById(R.id.shopOutBtnOnPubInter);
        appEndBtn = (Button)findViewById(R.id.appEndBtnOnPubInter);
        showInterstitialBtn = (Button)findViewById(R.id.showInterstitialBtn);
        customeAdSpaceKey = (EditText)findViewById(R.id.customeAdSpaceKeyOnPubInter);

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
}

