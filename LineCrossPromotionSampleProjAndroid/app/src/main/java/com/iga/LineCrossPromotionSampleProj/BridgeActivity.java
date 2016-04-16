package com.iga.LineCrossPromotionSampleProj;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by MikeHan on 2016-04-16.
 */
public class BridgeActivity extends Activity {

    private String appId, userKey, encryptedUserKey;
    private TextView appIdTv, userKeyTv, encryptedUserKeyTv;
    private Button advertiserBtn, offerwallBtn, interstitialBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bridge_layout);

        Bundle bundle = getIntent().getExtras();
        appId = bundle.getString("appId");
        userKey = bundle.getString("userKey");
        encryptedUserKey = bundle.getString("encryptedUserKey");

        makeUiComponents();

        advertiserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(AdvertiserActivity.class);
            }
        });

        offerwallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(PublisherOfferwallActivity.class);
            }
        });

        interstitialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(PublisherInterstitialActivity.class);
            }
        });
    }

    private void makeUiComponents() {

        appIdTv = (TextView)findViewById(R.id.lineAppIdOnBridge);
        userKeyTv = (TextView)findViewById(R.id.plainUserKeyOnBridge);
        encryptedUserKeyTv = (TextView)findViewById(R.id.encryptedUserKeyOnBridge);

        appIdTv.setText(appId);
        userKeyTv.setText(userKey);
        encryptedUserKeyTv.setText(encryptedUserKey);

        advertiserBtn = (Button)findViewById(R.id.advertiserBtn);
        offerwallBtn = (Button)findViewById(R.id.pubOfferwallBtn);
        interstitialBtn = (Button)findViewById(R.id.pubInterstitialBtn);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);

    }

    private void openActivity(Class name){

        Intent intent = new Intent(BridgeActivity.this, name);
        startActivity(intent);

    }
}
