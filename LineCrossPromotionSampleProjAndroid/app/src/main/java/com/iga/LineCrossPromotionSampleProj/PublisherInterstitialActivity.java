package com.iga.LineCrossPromotionSampleProj;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.line.crosspromotion.LineCrossPromotion;

/**
 * Created by MikeHan on 2016-04-16.
 */
public class PublisherInterstitialActivity extends Activity {

    Button loginBtn, shopOutBtn, appEndBtn, showInterstitialBtn;
    EditText customeAdSpaceKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pub_interstitial_layout);

        makeUiComponents();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LineCrossPromotion.setUserId("");
            }
        });

        shopOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        appEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        showInterstitialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }

    private void makeUiComponents() {
        loginBtn = (Button)findViewById(R.id.loginBtnOnPubInter);
        shopOutBtn = (Button)findViewById(R.id.shopOutBtnOnPubInter);
        appEndBtn = (Button)findViewById(R.id.appEndBtnOnPubInter);
        showInterstitialBtn = (Button)findViewById(R.id.showInterstitialBtn);
        customeAdSpaceKey = (EditText)findViewById(R.id.customeAdSpaceKeyOnPubInter);
    }
}

