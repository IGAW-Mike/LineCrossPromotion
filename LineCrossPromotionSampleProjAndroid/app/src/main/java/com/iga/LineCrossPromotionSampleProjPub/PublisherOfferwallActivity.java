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

import com.igaworks.adpopcorn.v2.core.AdSpotError;
import com.igaworks.adpopcorn.v2.core.IgawAdSpotListener;
import com.line.crosspromotion.LineCrossPromotion;

/**
 * Created by MikeHan on 2016-04-16.
 */
public class PublisherOfferwallActivity extends Activity {


    Button loginBtn, getOfferwallBtn, showOfferwallBtn;

    private String encrytedUserKey;
    private String OFFERWALL_ADSPOT_KEY = "";

    private String LOG_TAG = "LINE_BVT";
    private String logMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_offerwall_layout);

        makeUiComponents();

        Bundle bundle = getIntent().getExtras();
        encrytedUserKey = bundle.getString("EncryptedUserKey");

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineCrossPromotion.setUserId(encrytedUserKey);
                Log.d(LOG_TAG, "Login with encrytedUserKey : " + encrytedUserKey);
            }
        });

        getOfferwallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineCrossPromotion.getOfferwall(PublisherOfferwallActivity.this,
                        OFFERWALL_ADSPOT_KEY, registerIgawAdSpotListener());
            }
        });

        showOfferwallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineCrossPromotion.showOfferwall(PublisherOfferwallActivity.this, OFFERWALL_ADSPOT_KEY);
            }
        });
    }

    public IgawAdSpotListener registerIgawAdSpotListener(){
        IgawAdSpotListener listener = new IgawAdSpotListener() {
            @Override
            public void OnGetContentsSuccess(String adSpotKey, int contentsType, int adType) {
                // Called when getting offerwall contents is succeeded.
                logMessage = "OnGetContentsSuccess" +
                        "\nadSpotKey : " + adSpotKey +
                        "\ncontentType : " + contentsType +
                        "\nadType : " + adType;
                Log.d(LOG_TAG, logMessage);
            }

            @Override
            public void OnGetContentsFailure(String adSpotKey, AdSpotError adSpotError) {
                // Called with error info when getting offerwall content is failed.
                logMessage = "OnGetContentsFailure"+
                        "\nadSpotKey : " + adSpotKey +
                        "\nerrorCode : " + adSpotError.getErrorCode() +
                        "\nerrorMsg : " + adSpotError.getErrorMessage();
                Log.d(LOG_TAG, logMessage);
            }

            @Override
            public void OnShowContentsSuccess(String adSpotKey) {
                // Called when offerwall contents showing is succeeded.
                logMessage = "OnShowContentsSuccess" +
                        "\nadSpotKey : " + adSpotKey;
                Log.d(LOG_TAG, logMessage);
            }

            @Override
            public void OnShowContentsFailure(String adSpotKey, AdSpotError adSpotError) {
                // Called with error info when offerwall contents showing is failed.
                logMessage = "OnShowContentsFailure" +
                        "\nadSpotKey : " + adSpotKey +
                        "\nerrorCode : " + adSpotError.getErrorCode() +
                        "\nerrorMsg : " + adSpotError.getErrorMessage();
                Log.d(LOG_TAG, logMessage);
            }

            @Override
            public void OnCloseContents(String adSpotKey) {
                // Called when offerwall is closed.
                logMessage = "OnCloseContents" +
                        "\nadSpotKey : " + adSpotKey;
                Log.d(LOG_TAG,logMessage);
            }
        };
        return  listener;

    }

    // CALL startSession api
    @Override
    protected void onResume() {
        super.onResume();
        LineCrossPromotion.startSession(PublisherOfferwallActivity.this);
    }

    // CALL endSession api
    @Override
    protected void onPause() {
        super.onPause();
        LineCrossPromotion.endSession();
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void makeUiComponents() {
        loginBtn = (Button)findViewById(R.id.loginBtnOnPubOfferwall);
        getOfferwallBtn = (Button)findViewById(R.id.getOfferwallBtnOnPubOfferwall);
        showOfferwallBtn = (Button)findViewById(R.id.showOfferwallBtnOnPubOfferwall);

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
